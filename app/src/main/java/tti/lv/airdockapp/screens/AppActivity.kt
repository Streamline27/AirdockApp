package tti.lv.airdockapp.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle


import tti.lv.airdockapp.App
import tti.lv.airdockapp.screens.login.LoginActivity
import tti.lv.airdockapp.screens.main.MainActivity
import tti.lv.airdockapp.utilities.SharedPreferenceProvider
import javax.inject.Inject

class AppActivity : Activity() {

    @Inject lateinit var preferenceProvider: SharedPreferenceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).dependencyGraph.inject(this)

        val token = preferenceProvider.getToken()
        if (token.isNullOrBlank()) {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            })
        }
        else {
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            })
        }
        finish()
    }

}
