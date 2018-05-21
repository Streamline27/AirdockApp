package tti.lv.airdockapp.web.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import tti.lv.airdockapp.web.dto.RequestDTO

interface RequestApi {

    @GET("/api/worker/{workerId}/requests")
    fun getRequests(@Path("workerId") workerId : String) : Observable<List<RequestDTO>>

    @PUT("/api/request/{requestId}")
    fun updateRequest(
            @Body              requestDTO: RequestDTO,
            @Path("requestId") requestId : String
    ) : Observable<RequestDTO>

}