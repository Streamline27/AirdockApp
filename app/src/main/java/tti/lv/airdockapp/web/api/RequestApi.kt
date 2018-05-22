package tti.lv.airdockapp.web.api

import io.reactivex.Observable
import retrofit2.http.*
import tti.lv.airdockapp.web.dto.RequestDTO

interface RequestApi {

    @GET("/api/worker/{workerId}/requests")
    fun getRequests(@Path("workerId") workerId : String) : Observable<List<RequestDTO>>

    @PUT("/api/request/{requestId}")
    fun updateRequest(
            @Body              requestDTO: RequestDTO,
            @Path("requestId") requestId : String
    ) : Observable<RequestDTO>

    @DELETE("/api/request/{id}")
    fun deleteRequest(@Path("id") id : String) : Observable<String>

    @PUT("/api/request/{id}/status/{status}")
    fun updateTaskStatus(@Path("id") id : String, @Path("status") status : RequestDTO.Status) : Observable<RequestDTO>

    @POST("/api/request")
    fun createDraftRequest() : Observable<RequestDTO>
}