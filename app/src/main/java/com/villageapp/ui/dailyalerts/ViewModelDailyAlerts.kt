package com.villageapp.ui.dailyalerts

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.dailyalerts.ResponseMarkReadDailyAlert
import com.villageapp.models.dailyalerts.detail.ResponseDailyAlertDetail
import com.villageapp.models.dailyalerts.list.ResponseGetDailyAlertList
import com.villageapp.models.product.marksave.ResponseMarkFavourite
import com.villageapp.network.RestResponse


class ViewModelDailyAlerts(repository: RepositoryDailyAlerts) : BaseViewModel<RepositoryDailyAlerts>(repository) {

    var eventDailyAlertMarkRead = MutableLiveData<RestResponse<ResponseMarkReadDailyAlert>>()
    var eventsDailyAlertList = MutableLiveData<RestResponse<ResponseGetDailyAlertList>>()
    var eventsDailyAlertDetail = MutableLiveData<RestResponse<ResponseDailyAlertDetail>>()
    var eventDailyAlertFavouriteRemove = MutableLiveData<RestResponse<ResponseMarkFavourite>>()
    var eventDailyAlertFavourite = MutableLiveData<RestResponse<ResponseMarkFavourite>>()


    fun getDailyAlertList() {
        eventsDailyAlertList.postValue(RestResponse())

        repository.callAPIDailyAlertList(
            APIResponseCallback {
                eventsDailyAlertList.postValue(it)
            })
    }


    fun dailyAlertDetail(alertId: Int) {
        eventsDailyAlertDetail.postValue(RestResponse())

        repository.callAPIDailyAlertDetail(
            alertId,
            APIResponseCallback {
                eventsDailyAlertDetail.postValue(it)
            })
    }


    fun favouriteDailyAlert(alertId: Int) {

        if (isUserLoggedIn()) {
            eventDailyAlertFavourite.postValue(RestResponse())

            repository.callAPIMarkFavourite(
                alertId,
                APIResponseCallback {
                    eventDailyAlertFavourite.postValue(it)
                })
        } else {
            eventDailyAlertFavourite.postValue(RestResponse(RestResponse.Status.LOGIN))

        }


    }

    fun removeFavouriteDailyAlert(alertId: Int) {


        if (isUserLoggedIn()) {
            eventDailyAlertFavouriteRemove.postValue(RestResponse())

            repository.callAPIRemoveFavourite(
                alertId,
                APIResponseCallback {
                    eventDailyAlertFavouriteRemove.postValue(it)
                })
        } else {
            eventDailyAlertFavouriteRemove.postValue(RestResponse(RestResponse.Status.LOGIN))

        }


    }


    fun markReadDailyAlert(alertId: Int) {

        if (isUserLoggedIn()) {
            eventDailyAlertMarkRead.postValue(RestResponse())

            repository.callAPIMarkReadDailyAlert(
                alertId,
                APIResponseCallback {
                    eventDailyAlertMarkRead.postValue(it)
                })
        } else {
            eventDailyAlertMarkRead.postValue(RestResponse(RestResponse.Status.LOGIN))

        }


    }
}