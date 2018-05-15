package tti.lv.airdockapp.screens.main.tasks

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import tti.lv.airdockapp.domain.Task
import tti.lv.airdockapp.web.api.TaskApi
import tti.lv.airdockapp.web.dto.TaskDto
import java.text.FieldPosition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskListViewModel @Inject constructor(
        val taskApi : TaskApi
) {
    private val taskSelected = BehaviorSubject.create<TaskDto>()
    private val tasksFetched = BehaviorSubject.create<List<TaskDto>>()

    private var selectedPosition: Int? = null

    fun fetchTasks() {

        taskApi.getTasks()
                .subscribeOn(Schedulers.io())
                .subscribe{ tasks ->
            selectTask(0, tasks.first())
            tasksFetched.onNext(tasks)
        }
    }

    fun selectTask(position : Int, task : TaskDto) {
        taskSelected.onNext(task)
        selectedPosition = position
    }

    fun taskSelected() = taskSelected.observeOn(AndroidSchedulers.mainThread())
    fun tasks() = tasksFetched.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}