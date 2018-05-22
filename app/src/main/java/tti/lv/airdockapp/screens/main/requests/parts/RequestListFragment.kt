package tti.lv.airdockapp.screens.main.requests.parts


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
import tti.lv.airdockapp.screens.main.requests.RequestViewModel
import tti.lv.airdockapp.web.dto.RequestDTO
import javax.inject.Inject


class RequestListFragment : Fragment() {

    @Inject lateinit var mViewModel : RequestViewModel

    private lateinit var mViewAdapter: RequestListAdapter

    private val mDisp = mutableListOf<Disposable>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_request_list, container, false)
        (activity?.application as App).dependencyGraph.inject(this)

        val viewManager = LinearLayoutManager(context)
        mViewAdapter = RequestListAdapter(context!!, ArrayList())

        view.findViewById<RecyclerView>(R.id.listView_requests).apply {
            layoutManager = viewManager
            adapter = mViewAdapter
        }

        mDisp += mViewAdapter.itemClicks().subscribe{ (_, request) -> mViewModel.selectRequest(request) }

        mDisp += mViewModel.requestStatusChangeEvent().subscribe { req -> setRequestStatus(req.id, req.status) }
        mDisp += mViewModel.requestUpdateEvent().subscribe{ request -> updateRequest(request) }
        mDisp += mViewModel.requestSelectedEvent().subscribe{ request -> highlightRequest(request) }
        mDisp += mViewModel.requests().subscribe{ requests ->
            setRequests(requests)
            highlightRequest(mViewModel.getActiveRequest())
        }

        return view
    }

    private fun setRequestStatus(id: String, status: RequestDTO.Status) {
        mViewAdapter.changeRequestStatus(id, status)
    }

    private fun highlightRequest(request : RequestDTO?) {
        if (request != null) mViewAdapter.highlight(request)
    }

    private fun setRequests(requests : List<RequestDTO>) {
        mViewAdapter.setData(requests)
    }

    private fun updateRequest(request: RequestDTO) {
        mViewAdapter.updateRequest(request)
    }

    override fun onDetach() {
        super.onDetach()
        mDisp.forEach{ it.dispose() }
    }

}
