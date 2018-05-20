package tti.lv.airdockapp.screens.main.requests

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tti.lv.airdockapp.App

import tti.lv.airdockapp.R
import tti.lv.airdockapp.screens.main.requests.parts.RequestDetailsFragment
import tti.lv.airdockapp.screens.main.requests.parts.RequestListFragment
import javax.inject.Inject


class RequestsFragment : Fragment() {

    @Inject lateinit var mViewModel: RequestViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                      savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_requests, container, false)
        (activity?.application as App).dependencyGraph.inject(this)

        childFragmentManager.beginTransaction()
                .replace(R.id.request_list_container,    RequestListFragment())
                .replace(R.id.request_details_container, RequestDetailsFragment())
                .commit()

        mViewModel.fetchRequests()

        return view;
    }





    override fun onDetach() {
        super.onDetach()
    }

}
