package com.villageapp.ui.notification

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.notification.ResponseNotificationListing
import com.villageapp.models.notification.ResponseRegisterDevice
import com.villageapp.utils.Config

class RepositoryNotification : BaseRepository() {


    fun registerDeviceForNotification(callback: APIResponseCallback<ResponseRegisterDevice>) {
        val deviceRegId = preferenceManager.getDeviceTokenForFCM()

        deviceRegId?.let {

            sendApiCall(appRestService.registerDeviceForNotification(it), callback)
        }

    }


    fun unRegisterDeviceForNotification(callback: APIResponseCallback<ResponseRegisterDevice>) {
        val deviceRegId = preferenceManager.getDeviceTokenForFCM()

        deviceRegId?.let {

            sendApiCall(appRestService.unRegisterDeviceForNotification(it), callback)
        }
    }


    fun callAPINotificationList(

        callback: APIResponseCallback<ResponseNotificationListing>
    ) {
        sendApiCall(appRestService.getNotificationList(), callback)
    }

    fun saveNotificationSentToServer(isSentToServer: Boolean) {
        preferenceManager.savePreference(Config.SharedPreferences.PROPERTY_IS_FCM_SENT_TO_SERVER, isSentToServer)
    }

    fun getIfNotificationSentToServer(): Boolean {
        return  preferenceManager.getBooleanPreference(Config.SharedPreferences.PROPERTY_IS_FCM_SENT_TO_SERVER, false)
    }


}
