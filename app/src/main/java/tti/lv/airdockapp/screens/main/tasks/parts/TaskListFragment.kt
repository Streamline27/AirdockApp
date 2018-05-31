package tti.lv.airdockapp.screens.main.tasks.parts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.Disposable
import tti.lv.airdockapp.App
import tti.lv.airdockapp.R
import tti.lv.airdockapp.screens.main.tasks.TaskViewModel
import tti.lv.airdockapp.web.dto.TaskDTO
import javax.inject.Inject


class TaskListFragment : Fragment() {

    private val mDisp = mutableListOf<Disposable>()

    @Inject lateinit var mViewModel: TaskViewModel

    private lateinit var viewAdapter: TaskListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        (activity?.application as App).dependencyGraph.inject(this)

        val viewManager = LinearLayoutManager(context)
        viewAdapter = TaskListAdapter(context!!, ArrayList())

        view.findViewById<RecyclerView>(R.id.listView_tasks).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        mDisp += viewAdapter.itemClicks().subscribe{ (_, task) -> mViewModel.selectTask(task) }

        mDisp += mViewModel.taskStatusChangeEvent().subscribe{ task -> viewAdapter.changeTaskStatus(task.id, task.status) }
        mDisp += mViewModel.tasks().subscribe { tasks ->
            addTasks(tasks)
            mDisp += mViewModel.taskSelections().subscribe{ task -> highlightSelectedTask(task) }
        }

        return view
    }

    fun addTasks(tasks : List<TaskDTO>) {
        viewAdapter.tasks.clear()
        viewAdapter.tasks.addAll(tasks)
        viewAdapter.notifyDataSetChanged()
    }

    fun highlightSelectedTask(task: TaskDTO) {
        viewAdapter.highlight(task)
    }

    override fun onDetach() {
        super.onDetach()
        mDisp.forEach{ it.dispose() }
    }
}
