package tti.lv.airdockapp.screens.main.requests.parts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import tti.lv.airdockapp.R
import tti.lv.airdockapp.web.dto.RequestDTO

class RequestListAdapter(private val requests : MutableList<RequestDTO>) : RecyclerView.Adapter<RequestListAdapter.ViewHolder>() {

    class ViewHolder(
            val view           : View,
            val textReqId      : TextView,
            val textReqCaption : TextView
    ) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RequestListAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
                                    .inflate(R.layout.item_request, parent, false)

        val textReqId      = view.findViewById<TextView>(R.id.item_request_id);
        val textReqCaption = view.findViewById<TextView>(R.id.item_request_caption)

        return ViewHolder(view, textReqId, textReqCaption)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val req = requests[position]

            textReqId.text      = req.id
            textReqCaption.text = req.title
        }
    }

    override fun getItemCount() = requests.size


    fun setData(requestsNew : List<RequestDTO>) {
        requests.clear()
        requests.addAll(requestsNew)
        notifyDataSetChanged()
    }

}