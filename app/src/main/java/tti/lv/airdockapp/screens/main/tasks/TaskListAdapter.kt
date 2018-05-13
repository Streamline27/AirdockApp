package tti.lv.airdockapp.screens.main.tasks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import tti.lv.airdockapp.R
import tti.lv.airdockapp.web.dto.TaskDto

class TaskListAdapter(
        context : Context?,
        private val tasks : List<TaskDto>) : ArrayAdapter<TaskDto>(context, 0, tasks.toMutableList())
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        val caption = view.findViewById<TextView>(R.id.item_task_caption);
        caption.text = tasks[position].title
        return view
    }

}