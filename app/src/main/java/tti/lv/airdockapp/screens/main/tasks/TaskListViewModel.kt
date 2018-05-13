package tti.lv.airdockapp.screens.main.tasks

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tti.lv.airdockapp.web.api.TaskApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskListViewModel @Inject constructor(
        val taskApi : TaskApi
) {

    fun tasks() = taskApi.getTasks().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}