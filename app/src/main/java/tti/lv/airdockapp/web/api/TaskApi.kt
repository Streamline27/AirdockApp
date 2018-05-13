package tti.lv.airdockapp.web.api

import io.reactivex.Observable
import retrofit2.http.GET
import tti.lv.airdockapp.web.dto.TaskDto

interface TaskApi {
    @GET("/api/tasks")
    fun getTasks() : Observable<List<TaskDto>>
}