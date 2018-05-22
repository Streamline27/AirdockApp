package tti.lv.airdockapp.screens.main.tasks.parts


import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_task_details.*
import tti.lv.airdockapp.App

import tti.lv.airdockapp.R
import tti.lv.airdockapp.screens.main.tasks.TaskViewModel
import tti.lv.airdockapp.utilities.idWithPrefix
import tti.lv.airdockapp.utilities.toDateWithTime
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

        mDisp += mViewModel.taskSelections().subscribe{ task -> setActiveTask(task) }
        mDisp += mViewModel.taskStatusChanges().subscribe { status -> setTaskStatus( status )}

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDisp += btnBegin.clicks().subscribe{ mViewModel.updateTaskStatus(TaskDTO.Status.IN_PROGRESS ) }
        mDisp += btnComplete.clicks().subscribe{ mViewModel.updateTaskStatus(TaskDTO.Status.DONE ) }
        mDisp += btnSuspend.clicks().subscribe{ mViewModel.updateTaskStatus(TaskDTO.Status.SUSPENDED ) }
        mDisp += btnContinue.clicks().subscribe{ mViewModel.updateTaskStatus(TaskDTO.Status.IN_PROGRESS)}
        mDisp += btnTodo.clicks().subscribe{ mViewModel.updateTaskStatus(TaskDTO.Status.TODO)}
//        mDisp += btnChangeStatus.clicks().subscribe{ openSelectStatusDialog() }

    }

    fun setActiveTask(task : TaskDTO) {
        with(task) {
            textWorkOrderId.text   = task.workOrder.title
            textTaskId.text        = this.idWithPrefix()
            textTaskTitle.text     = title
            textStatus.text        = status.toPrettyString()
            textDateCreated.text   = creationDate.toDateWithTime()
            textDescription.text   = description
            textDatePlannedOn.text = startDate.toShortDateFormat() + " - " + endDate.toShortDateFormat()
            btnChangeStatus.text   = status.toPrettyString()
        }
    }

    fun setTaskStatus(status : TaskDTO.Status) {
        val s = status.toPrettyString()
        btnChangeStatus.text = s
        textStatus.text = s

        if (status == TaskDTO.Status.CANCELED) {
            btnBegin.visibility = INVISIBLE
            btnComplete.visibility = GONE
            btnContinue.visibility = GONE
            btnSuspend.visibility = GONE
            btnTodo.visibility = GONE
        }
        else if (status == TaskDTO.Status.SUSPENDED) {
            btnBegin.visibility = GONE
            btnComplete.visibility = GONE
            btnContinue.visibility = VISIBLE
            btnSuspend.visibility = GONE
            btnTodo.visibility = VISIBLE
        }
        else if (status == TaskDTO.Status.DONE) {
            btnBegin.visibility = GONE
            btnComplete.visibility = GONE
            btnContinue.visibility = VISIBLE
            btnSuspend.visibility = GONE
            btnTodo.visibility = GONE
        }
        else if (status == TaskDTO.Status.IN_PROGRESS) {
            btnBegin.visibility = GONE
            btnComplete.visibility = VISIBLE
            btnContinue.visibility = GONE
            btnSuspend.visibility = VISIBLE
            btnTodo.visibility = VISIBLE
        }
        else {
            btnBegin.visibility = VISIBLE
            btnComplete.visibility = GONE
            btnContinue.visibility = GONE
            btnSuspend.visibility = GONE
            btnTodo.visibility = GONE
        }
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
