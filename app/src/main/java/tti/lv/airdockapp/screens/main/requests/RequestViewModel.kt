package tti.lv.airdockapp.screens.main.requests

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import tti.lv.airdockapp.utilities.SharedPreferenceProvider
import tti.lv.airdockapp.web.api.RequestApi
import tti.lv.airdockapp.web.dto.RequestDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestViewModel @Inject constructor(
        private val requestApi : RequestApi,
        private val preferences : SharedPreferenceProvider
){
    private val requestsState         = BehaviorSubject.create<List<RequestDTO>>()
    private val requestSelectedState  = BehaviorSubject.create<RequestDTO>()
    private val requestStatusState = BehaviorSubject.create<RequestDTO.Status>()

    private val requestSelectedEvent     = PublishSubject.create<RequestDTO>()
    private val editClickedEvent         = PublishSubject.create<Any>()
    private val requestUpdatedEvent      = PublishSubject.create<RequestDTO>()
    private val requestStatusChangeEvent =  PublishSubject.create<RequestDTO>()


    private val currentUserId : String = preferences.getUserId();

    init {
        requestSelectedState.subscribe{ req -> requestSelectedEvent.onNext(req) }
        requestSelectedState.subscribe{ req -> requestStatusState.onNext(req.status)}
    }

    fun fetchRequests() {
        requestApi.getRequests(currentUserId)
                .subscribeOn(Schedulers.io())
                .subscribe{ requests ->
                    requestsState.onNext(requests)
                    if (!someRequestIsSelected() && requests.isNotEmpty()) requestSelectedState.onNext(requests.first())
                }
    }

    fun updateSelectedRequest(title : String, description : String) {
        if (someRequestIsSelected()) {
            val id = requestSelectedState.value.id

            val requestNew = RequestDTO(
                    id          = id,
                    title       = title,
                    description = description
            )
            requestApi.updateRequest(requestNew, id)
                    .subscribeOn(Schedulers.io())
                    .subscribe { request ->
                        updateRequestInState(request)
                        requestSelectedState.onNext(request)
                        requestUpdatedEvent.onNext(request)
                    }
        }
    }


    private fun updateRequestInState(request: RequestDTO) {
        val requests = requestsState.value
        if (requests != null) {
            val index = requests.indexOfFirst { it.id == request.id }
            if (index != -1) {
                val newRequests = requests.toMutableList().apply {
                    this[index] = request
                }
                requestsState.onNext(newRequests)
            }
        }
    }

    fun setRequestStatus(status : RequestDTO.Status) {
        if (someRequestIsSelected()) {
            val id = requestSelectedState.value.id
            requestApi.updateTaskStatus(id, status)
                    .subscribeOn(Schedulers.io())
                    .subscribe{ request ->
                        requestStatusState.onNext(request.status)
                        requestStatusChangeEvent.onNext(request)
                    }
        }
    }

    fun deleteRequest() {
        if (someRequestIsSelected()) {
            requestApi.deleteRequest(requestSelectedState.value.id)
                    .subscribeOn(Schedulers.io())
                    .subscribe{ id ->
                        val requests = requestsState.value
                        val selectedRequest = requestSelectedState.value
                        if (requests != null) {
                            val index = requests.indexOfFirst { it.id == selectedRequest.id }
                            val without = requests.toMutableList().apply { removeAt(index) }
                            val selectedPos = index-1
                            requestsState.onNext(without)
                            requestSelectedState.onNext(without[selectedPos])
                        }
                    }
        }
    }


    fun deleteRequestFromState() {

    }

    fun createDraftRequest() {
        requestApi.createDraftRequest()
                .subscribeOn(Schedulers.io())
                .subscribe{ request ->
                    if (someRequestIsSelected()) {
                        val requests = requestsState.value.plus(request)
                        requestsState.onNext(requests)
                        requestSelectedState.onNext(request)
                    }
                }
    }

    fun editClicks() {
        editClickedEvent.onNext(Any())
    }

    fun selectRequest(request : RequestDTO) {
        requestSelectedState.onNext(request)
    }

    fun getActiveRequest() = if (someRequestIsSelected()) requestSelectedState.value else null
    fun someRequestIsSelected() = requestSelectedState.value != null

    fun requestUpdateEvent()    = requestUpdatedEvent.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    fun requestEditClickEvent() = editClickedEvent.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    fun requestSelectedEvent()  =  requestSelectedEvent.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    fun requestStatusChangeEvent() = requestStatusChangeEvent.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun requests()          = requestsState.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    fun requestSelections() = requestSelectedState.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    fun requestStatuses()   = requestStatusState.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}