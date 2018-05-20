package tti.lv.airdockapp.screens.main.requests

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    private val requestsFetched  = PublishSubject.create<List<RequestDTO>>()

    private val currentUserId : String = preferences.getUserId();

    fun fetchRequests() {
        requestApi.getRequests(currentUserId)
                .subscribeOn(Schedulers.io())
                .subscribe{ requests ->
                    requestsFetched.onNext(requests)
                }
    }

    fun requests() = requestsFetched.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}