package tti.lv.airdockapp.screens.main.tasks

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tti.lv.airdockapp.App

import tti.lv.airdockapp.R
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TasksFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    @Inject lateinit var mViewModel: TaskListViewModel

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
                .add(R.id.task_details_container, TaskDescriptionFragment.newInstance("", ""))
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
