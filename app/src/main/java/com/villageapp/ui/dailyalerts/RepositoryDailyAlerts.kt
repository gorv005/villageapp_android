package com.villageapp.ui.dailyalerts

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.dailyalerts.ResponseMarkReadDailyAlert
import com.villageapp.models.dailyalerts.detail.ResponseDailyAlertDetail
import com.villageapp.models.dailyalerts.list.ResponseGetDailyAlertList
import com.villageapp.models.meal.consume.list.ResponseMealConsumedList
import com.villageapp.models.product.marksave.ResponseMarkFavourite

class RepositoryDailyAlerts : BaseRepository() {

    fun callAPIDailyAlertDetail(
        alertId: Int,
        callback: APIResponseCallback<ResponseDailyAlertDetail>
    ) {
        sendApiCall(appRestService.getDailyAlertDetail(alertId), callback)
    }


    fun callAPIDailyAlertList(

        callback: APIResponseCallback<ResponseGetDailyAlertList>
    ) {
        sendApiCall(appRestService.getDailyAlertList(), callback)
    }


    fun callAPIMarkReadDailyAlert(
        alertId: Int,
        callback: APIResponseCallback<ResponseMarkReadDailyAlert>
    ) {
        sendApiCall(appRestService.markDailyAlertRead(alertId), callback)
    }



    fun callAPIMarkFavourite(
        alertId: Int,
        callback: APIResponseCallback<ResponseMarkFavourite>
    ) {
        sendApiCall(appRestService.markDailyAlertFavourite(alertId), callback)
    }


    fun callAPIRemoveFavourite(
        alertId: Int,
        callback: APIResponseCallback<ResponseMarkFavourite>
    ) {
        sendApiCall(appRestService.removeDailyAlertFavourite(alertId), callback)
    }

}