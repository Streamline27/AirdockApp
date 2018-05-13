package tti.lv.airdockapp.web.api

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import tti.lv.airdockapp.web.dto.LoginDto

interface AuthorizationApi {

    @POST("/login")
    fun getToken(@Body loginDto: LoginDto) : Observable<Response<Any>>
}