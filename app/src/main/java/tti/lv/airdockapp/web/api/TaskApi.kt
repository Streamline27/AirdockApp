package tti.lv.airdockapp.web.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import tti.lv.airdockapp.web.dto.TaskDTO
import tti.lv.airdockapp.web.dto.TaskSmallDTO

interface TaskApi {
    @GET("/api/tasks")
    fun getTasks() : Observable<List<TaskDTO>>

    @PUT("/api/task/{id}/status/{status}")
    fun updateTaskStatus(@Path("id") id : String, @Path("status") status : TaskDTO.Status) : Observable<TaskSmallDTO>
}