package tti.lv.airdockapp.screens.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import tti.lv.airdockapp.utilities.JwtHelper
import tti.lv.airdockapp.web.api.AuthorizationApi
import tti.lv.airdockapp.utilities.SharedPreferenceProvider
import tti.lv.airdockapp.web.dto.LoginDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor(

        private val preferenceProvider: SharedPreferenceProvider,
        private val authorizationApi: AuthorizationApi,
        private val jwtHelper: JwtHelper
) {

    var username : String = ""
    var password : String = ""

    private var authorizationSuccess = PublishSubject.create<Any>()
    private var authorizationFail    = PublishSubject.create<Any>()
    private var authorizationStart   = PublishSubject.create<Any>()

    fun authorizeUser() {

        authorizationStart.onNext(Any())

        authorizationApi.getToken(LoginDTO(username, password))
                .subscribeOn(Schedulers.io())
                .subscribe{ response ->

                    if (response.isSuccessful) {
                        val authHeader = response.headers().get("Authorization")
                        val token = getToken(authHeader)

                        preferenceProvider.storeToken(token ?: "")
                        preferenceProvider.storeUserId(jwtHelper.extractUserId(token))
                        authorizationSuccess.onNext(Any())
                    }
                    else {
                        authorizationFail.onNext(Any())
                    }
                }
    }



    fun authorizationFinish()  = authorizationSuccess.mergeWith(authorizationFail).observeOn(AndroidSchedulers.mainThread())
    fun authorizationFail()    = authorizationFail.observeOn(AndroidSchedulers.mainThread())
    fun authorizationSuccess() = authorizationSuccess.observeOn(AndroidSchedulers.mainThread())
    fun authorizationStart()   = authorizationStart.observeOn(AndroidSchedulers.mainThread())

    private fun getToken(header : String?) =
            if (header != null) header.split(" ")[1]
            else null
}