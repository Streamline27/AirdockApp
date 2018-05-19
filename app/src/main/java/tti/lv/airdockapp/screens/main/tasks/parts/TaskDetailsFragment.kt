package tti.lv.airdockapp.screens.main.tasks.parts


import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_task_details.*
import tti.lv.airdockapp.App

import tti.lv.airdockapp.R
import tti.lv.airdockapp.screens.main.tasks.TaskViewModel
import tti.lv.airdockapp.utilities.idWithPrefix
import tti.lv.airdockapp.utilities.toDateWithTime
import tti.lv.airdockapp.utilities.toPrettyString
import tti.lv.airdockapp.utilities.toShortDateFormat
import tti.lv.airdockapp.web.dto.TaskDTO
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TaskDetailsFragment : Fragment() {

    @Inject lateinit var mViewModel: TaskViewModel

    private val mDisp = mutableListOf<Disposable>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)
        (activity?.application as App).dependencyGraph.inject(this)

        mDisp += mViewModel.taskSelected().subscribe{ task -> setActiveTask(task) }
        mDisp += mViewModel.taskStatusChanged().subscribe { task -> setTaskStatus( task.status )}

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDisp += btnChangeStatus.clicks().subscribe{ openSelectStatusDialog() }
    }

    fun setActiveTask(task : TaskDTO) {
        with(task) {
            textWorkOrderId.text  = task.workOrder.title
            textTaskId.text       = this.idWithPrefix()
            textTaskTitle.text    = title
            textStatus.text        = status.toString()
            textDateCreated.text   = creationDate.toDateWithTime()
            textDescription.text   = description
            textDatePlannedOn.text = startDate.toShortDateFormat() + " - " + endDate.toShortDateFormat()
        }
    }

    fun setTaskStatus(status : TaskDTO.Status) {
        textStatus.text = status.toPrettyString()
    }

    fun openSelectStatusDialog() {

        val items =
                TaskDTO.Status.assignableValues()
                        .filter { it != mViewModel.getSelectedTaskStatus()}

        val stringItems =
                items.map { it.toPrettyString() }
                        .toTypedArray()

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Select status:")
                .setItems(stringItems, object  : DialogInterface.OnClickListener {
                              override fun onClick(dialog: DialogInterface?, index: Int) {
                                     mViewModel.updateTaskStatus(items[index])
                         }
                })
                .create()
                .show()
    }

    override fun onDetach() {
        super.onDetach()
        mDisp.forEach{ it.dispose() }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TaskDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
