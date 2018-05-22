package tti.lv.airdockapp.screens.main.tasks

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
    private val taskSelectedState  = BehaviorSubject.create<TaskDTO>()
    private val taskStatusState    = BehaviorSubject.create<TaskDTO.Status>()

//    private val tasksFetched  = BehaviorSubject.create<List<TaskDTO>>()
    private val tasksFetched  = PublishSubject.create<List<TaskDTO>>()
    private val statusChanged = BehaviorSubject.create<TaskSmallDTO>()

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
        val selectedTask = taskSelectedState.value
        if (selectedTask != null) {
            taskApi.updateTaskStatus(selectedTask.id, status)
                    .subscribeOn(Schedulers.io())
                    .subscribe{ task ->
                        statusChanged.onNext(task)
                        taskStatusState.onNext(task.status)
                    }
        }
    }

    fun currentStatus() = taskSelectedState.value.status

    fun selectTask(task : TaskDTO) {
        taskSelectedState.onNext(task)
        taskStatusState.onNext(task.status)
    }

    fun someStatusIsSelected() = taskSelectedState.value != null
    fun getSelectedTaskStatus() = this.taskSelectedState.value.status

    fun taskStatusChanges() = taskStatusState.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun taskSelections() = taskSelectedState.observeOn(AndroidSchedulers.mainThread())
    fun tasks() = tasksFetched.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())



    fun taskStatusChangeEvent() = statusChanged.observeOn(AndroidSchedulers.mainThread())
}