package tti.lv.airdockapp.screens.main.tasks

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import io.reactivex.disposables.Disposable
import tti.lv.airdockapp.App
import tti.lv.airdockapp.R
import tti.lv.airdockapp.domain.Task
import tti.lv.airdockapp.web.dto.TaskDto
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TaskListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val mDisp = mutableListOf<Disposable>()

    @Inject lateinit var mViewModel: TaskListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TaskListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        (activity?.application as App).dependencyGraph.inject(this)

        viewManager = LinearLayoutManager(context)
        viewAdapter = TaskListAdapter(ArrayList())

        recyclerView = view.findViewById<RecyclerView>(R.id.listView_tasks).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        mDisp += mViewModel.tasks().subscribe{ tasks -> addTasks(tasks) }
        mDisp += mViewModel.taskSelected().subscribe{ task -> Toast.makeText(context, task.title, Toast.LENGTH_SHORT ).show() }

        mDisp += viewAdapter.itemClicks().subscribe{ (position, task) -> mViewModel.selectTask(position, task) }

        return view
    }

    fun addTasks(tasks : List<TaskDto>) {
        viewAdapter.tasks.addAll(tasks)
        viewAdapter.notifyDataSetChanged()
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TaskListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onDetach() {
        super.onDetach()
        mDisp.forEach{ it.dispose() }
    }
}
