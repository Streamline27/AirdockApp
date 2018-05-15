package tti.lv.airdockapp.screens.main.tasks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import tti.lv.airdockapp.R
import tti.lv.airdockapp.web.dto.TaskDto

class TaskListAdapter(val tasks: MutableList<TaskDto>) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private val itemClicks = PublishSubject.create<Pair<Int, TaskDto>>()

    fun itemClicks() : Observable<Pair<Int, TaskDto>> = itemClicks

    inner class ViewHolder(
            val view: View,
            val captionTextView: TextView) : RecyclerView.ViewHolder(view)
    {
        init {
            view.clicks().subscribe{ itemClicks.onNext(Pair(layoutPosition, tasks[layoutPosition])) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        val captionTextView = view.findViewById<TextView>(R.id.item_task_caption)

        return ViewHolder(view, captionTextView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.captionTextView.text = tasks[position].title
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = tasks.size
}