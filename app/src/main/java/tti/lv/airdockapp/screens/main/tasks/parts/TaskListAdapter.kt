package tti.lv.airdockapp.screens.main.tasks.parts

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import tti.lv.airdockapp.R
import tti.lv.airdockapp.utilities.getImgResource
import tti.lv.airdockapp.utilities.idWithPrefix
import tti.lv.airdockapp.web.dto.TaskDTO

class TaskListAdapter(val ctx : Context, val tasks: MutableList<TaskDTO>) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private val itemClicks = PublishSubject.create<Pair<Int, TaskDTO>>()

    @Volatile private var selectedPos : Int = -1;

    fun itemClicks() : Observable<Pair<Int, TaskDTO>> = itemClicks

    inner class ViewHolder(
            val view: View,
            val idTextView : TextView,
            val imgStatus : ImageView,
            val captionTextView: TextView
    ) : RecyclerView.ViewHolder(view)
    {
        init {
            view.clicks().subscribe{
                itemClicks.onNext(Pair(layoutPosition, tasks[layoutPosition]))
            }
        }
    }

    fun highlight(task: TaskDTO) {
        val position = tasks.indexOfFirst { it.id == task.id }
        if (position != -1) {
            notifyItemChanged(selectedPos)
            selectedPos = position
            notifyItemChanged(selectedPos)
        }
        else selectedPos = 0;
    }

    fun changeTaskStatus(taskId : String, status : TaskDTO.Status) {
        val index = tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            tasks[index].status = status
            notifyItemChanged(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        val captionTextView = view.findViewById<TextView>(R.id.item_task_caption)
        val idTextView = view.findViewById<TextView>(R.id.item_task_id)
        val imgStatus = view.findViewById<ImageView>(R.id.imgStatus)

        return ViewHolder(view, idTextView, imgStatus, captionTextView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        with(holder) {
            captionTextView.text = task.title
            idTextView.text = task.idWithPrefix()
            imgStatus.setImageResource(task.status.getImgResource())

            if (selectedPos == position) view.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorLightGrey))
            else                         view.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorWhite))
        }
    }

    override fun getItemCount() = tasks.size


}