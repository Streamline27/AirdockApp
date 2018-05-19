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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TasksFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    @Inject lateinit var mViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        (activity?.application as App).dependencyGraph.inject(this)

        childFragmentManager.beginTransaction()
                .add(R.id.task_list_container, TaskListFragment.newInstance("", ""))
                .add(R.id.task_details_container, TaskDetailsFragment.newInstance("", ""))
                .add(R.id.task_history_container, TaskHistoryFragment.newInstance("", ""))
                .commit()

        mViewModel.fetchTasks()

        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TasksFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
