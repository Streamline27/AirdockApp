package tti.lv.airdockapp.screens.main.requests.parts

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_request_edit.*
import tti.lv.airdockapp.App
import tti.lv.airdockapp.R
import tti.lv.airdockapp.screens.main.requests.RequestViewModel
import tti.lv.airdockapp.web.dto.RequestDTO
import javax.inject.Inject

class RequestEditActivity : AppCompatActivity() {

    @Inject lateinit var mViewModel : RequestViewModel

    private var mProgressDialog: ProgressDialog? = null

    private val mDisp = mutableListOf<Disposable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_edit)
        (application as App).dependencyGraph.inject(this)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mDisp += mViewModel.requestSelections().subscribe{ request -> setActiveRequest(request) }

        mDisp += fab.clicks().subscribe{
            showProgressDialog()
            updateRequest()
        }
        mDisp += mViewModel.requestUpdateEvent().subscribe{
            Toast.makeText(this, "Request updated successfully", Toast.LENGTH_SHORT).show()
            dismissDialog()
        }
    }

    private fun updateRequest() {
        mViewModel.updateSelectedRequest(
                title       = textRequestEditTitle.text.toString(),
                description = editTextRequestDescription.text.toString()
        )
    }

    fun setActiveRequest(request : RequestDTO) {
        textRequestEditTitle.setText(request.title)
        textRequestEditId.setText(request.id)
        editTextRequestDescription.setText(request.description)
    }

    private fun showProgressDialog() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.isIndeterminate = true
        mProgressDialog?.setMessage("Updating...")
        mProgressDialog?.show()
    }

    private fun dismissDialog() {
        if (mProgressDialog!= null) mProgressDialog?.dismiss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisp.forEach{ it.dispose() }
    }
}
