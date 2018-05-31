package tti.lv.airdockapp.screens.main.tasks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tti.lv.airdockapp.App

import tti.lv.airdockapp.R
import tti.lv.airdockapp.screens.main.tasks.parts.TaskDetailsFragment
import tti.lv.airdockapp.screens.main.tasks.parts.TaskHistoryFragment
import tti.lv.airdockapp.screens.main.tasks.parts.TaskListFragment
import javax.inject.Inject


class TasksFragment : Fragment() {

    @Inject lateinit var mViewModel: TaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        (activity?.application as App).dependencyGraph.inject(this)

        childFragmentManager.beginTransaction()
                .replace(R.id.task_list_container, TaskListFragment())
                .replace(R.id.task_details_container, TaskDetailsFragment())
                .replace(R.id.task_history_container, TaskHistoryFragment())
                .commit()

        mViewModel.fetchTasks()

        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }
}
