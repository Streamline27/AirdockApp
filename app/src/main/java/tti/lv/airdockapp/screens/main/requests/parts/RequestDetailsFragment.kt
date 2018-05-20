package tti.lv.airdockapp.screens.main.requests.parts


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.fragment_request_details.*

import tti.lv.airdockapp.R


class RequestDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_request_details, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnEdit.clicks().subscribe{ launchEditRequestActivity() }
    }


    fun launchEditRequestActivity() {
        if (activity != null) {
            activity!!.startActivity(Intent(context, RequestEditActivity::class.java))
        }
    }


}
