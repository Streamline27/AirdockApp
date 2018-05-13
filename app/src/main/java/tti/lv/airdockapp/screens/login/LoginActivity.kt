package tti.lv.airdockapp.screens.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*
import tti.lv.airdockapp.App


import tti.lv.airdockapp.R
import tti.lv.airdockapp.screens.main.MainActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject lateinit var mViewModel: LoginViewModel

    private val mDisp = mutableListOf<Disposable>()
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).dependencyGraph.inject(this)
        setContentView(R.layout.activity_login)

        usernameEditText.textChanges().subscribe{ username -> mViewModel.username = username.toString() }
        passwordEditText.textChanges().subscribe{ password -> mViewModel.password = password.toString() }

        btnLogin.clicks().subscribe{
            mViewModel.authorizeUser()
        }

        mDisp += mViewModel.authorizationStart().subscribe{ showProgressDialog() }
        mDisp += mViewModel.authorizationFinish().subscribe{ dismissDialog() }
        mDisp += mViewModel.authorizationFail().subscribe{ Toast.makeText(this, "Authorization failed", Toast.LENGTH_SHORT).show() }
        mDisp += mViewModel.authorizationSuccess().subscribe{ goToMainActivity() }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showProgressDialog() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.isIndeterminate = true
        mProgressDialog?.setMessage("Authenticating...")
        mProgressDialog?.show()
    }

    private fun dismissDialog() {
        if (mProgressDialog!= null) mProgressDialog?.dismiss()
    }

    override fun onDestroy() {
        mDisp.forEach{ it.dispose() }
        super.onDestroy()
    }
}
