package tti.lv.airdockapp.screens.main.requests.parts

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.subjects.PublishSubject
import tti.lv.airdockapp.R
import tti.lv.airdockapp.web.dto.RequestDTO

class RequestListAdapter(
        private val ctx : Context,
        private val requests : MutableList<RequestDTO>
) : RecyclerView.Adapter<RequestListAdapter.ViewHolder>() {

    private val itemClicks = PublishSubject.create<Pair<Int, RequestDTO>>()

    @Volatile private var selectedPos : Int = -1;

    inner class ViewHolder(
            val view           : View,
            val textReqId      : TextView,
            val textReqCaption : TextView,
            val imgStatus      : ImageView
    ) : RecyclerView.ViewHolder(view)
    {
        init {
            view.clicks().subscribe{
                itemClicks.onNext(Pair(layoutPosition, requests[layoutPosition]))
            }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) : RequestListAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
                                    .inflate(R.layout.item_request, parent, false)

        val textReqId      = view.findViewById<TextView>(R.id.item_request_id);
        val textReqCaption = view.findViewById<TextView>(R.id.item_request_caption)
        val imageStatus    = view.findViewById<ImageView>(R.id.imgStatus)

        return ViewHolder(view, textReqId, textReqCaption, imageStatus)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val req = requests[position]

            textReqId.text      = req.id
            textReqCaption.text = req.title
            imgStatus.setImageResource(req.status.getImageResource())

            if (selectedPos == position) view.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorItemSelected))
            else                         view.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorWhite))
        }
    }

    override fun getItemCount() = requests.size

    fun itemClicks() = itemClicks;

    fun highlight(request: RequestDTO) {
        val position = requests.indexOfFirst { it.id == request.id }
        if (position != -1) {
            notifyItemChanged(selectedPos)
            selectedPos = position
            notifyItemChanged(selectedPos)
        }
        else selectedPos = 0;
    }

    fun changeRequestStatus(requestId : String, status : RequestDTO.Status) {
        val position = requests.indexOfFirst { it.id == requestId }
        if (position != -1) {
            requests[position].status = status
            notifyItemChanged(position)
        }
    }

    fun updateRequest(request: RequestDTO) {
        val position = requests.indexOfFirst { it.id == request.id }
        if (position != -1) {
            requests[position] = request
            notifyItemChanged(position)
        }
    }

    fun setData(requestsNew : List<RequestDTO>) {
        requests.clear()
        requests.addAll(requestsNew)
        notifyDataSetChanged()
    }

}