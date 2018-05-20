package tti.lv.airdockapp.utilities

import android.content.Context
import android.content.SharedPreferences


class SharedPreferenceProvider (
        context: Context
) {
    private val sharedPreferences : SharedPreferences = context.getSharedPreferences("tti.lv.airdock.FILES", Context.MODE_PRIVATE);

    fun storeToken(token : String) {
        with(sharedPreferences.edit()) {

            putString("auth_token", token)
            apply()
        }
    }

    fun eraseToken() {
        with(sharedPreferences.edit()) {
            remove("auth_token")
            apply()
        }
    }

    fun getToken() : String? = sharedPreferences.getString("auth_token", null)


    fun storeUserId(userId : String) {
        with(sharedPreferences.edit()) {
            putString("USER_ID", userId)
            apply()
        }
    }

    fun getUserId() : String = sharedPreferences.getString("USER_ID", null)


}