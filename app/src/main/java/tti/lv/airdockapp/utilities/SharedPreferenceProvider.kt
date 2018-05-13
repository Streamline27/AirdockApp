package tti.lv.airdockapp.utilities

import android.content.Context
import tti.lv.airdockapp.domain.User
import javax.inject.Inject
import javax.inject.Singleton


class SharedPreferenceProvider (
        private val context: Context
) {

    private var token : String? = null
    private var user : User? = null

    fun storeToken(token : String) {
        this.token = token
    }

    fun receiveToken() : String? {
        return this.token
    }

    fun storeUser(user : User) {
        this.user = user
    }

}