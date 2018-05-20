package tti.lv.airdockapp.screens.main.tasks

import android.content.SharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import tti.lv.airdockapp.utilities.SharedPreferenceProvider
import tti.lv.airdockapp.web.api.TaskApi
import tti.lv.airdockapp.web.dto.TaskDTO
import tti.lv.airdockapp.web.dto.TaskSmallDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskViewModel @Inject constructor(
        private val taskApi : TaskApi,
        private val preferences: SharedPreferenceProvider
) {
    private val taskSelected  = BehaviorSubject.create<TaskDTO>()
//    private val tasksFetched  = BehaviorSubject.create<List<TaskDTO>>()
    private val tasksFetched  = PublishSubject.create<List<TaskDTO>>()
    private val statusChanged = PublishSubject.create<TaskSmallDTO>()

    /**
     * TODO: Fetch tasks first time and on push notification then
     */
    fun fetchTasks() {

        taskApi.getTasks(preferences.getUserId())
                .subscribeOn(Schedulers.io())
                .subscribe{ tasks ->
            tasksFetched.onNext(tasks)
            if (!someStatusIsSelected() && tasks.isNotEmpty()) selectTask(tasks.first())
        }
    }

    fun updateTaskStatus(status : TaskDTO.Status) {
        val selectedTask = taskSelected.value
        if (selectedTask != null) {
            taskApi.updateTaskStatus(selectedTask.id, status)
                    .subscribeOn(Schedulers.io())
                    .subscribe{ task ->
                        statusChanged.onNext(task)
                    }
        }
    }

    fun selectTask(task : TaskDTO) {
        taskSelected.onNext(task)
    }

    fun someStatusIsSelected() = taskSelected.value != null
    fun getSelectedTaskStatus() = this.taskSelected.value.status
    fun getSelectedTask() = taskSelected.value

    fun taskSelected() = taskSelected.observeOn(AndroidSchedulers.mainThread())
    fun tasks() = tasksFetched.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    fun taskStatusChanged() = statusChanged.observeOn(AndroidSchedulers.mainThread())
}