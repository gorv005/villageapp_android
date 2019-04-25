package com.villageapp.ui.notification

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.notification.ResponseNotificationListing
import com.villageapp.models.notification.ResponseRegisterDevice
import com.villageapp.network.RestResponse

class ViewModelNotification(repository: RepositoryNotification) : BaseViewModel<RepositoryNotification>(repository) {

    var eventUnRegisterDevice = MutableLiveData<RestResponse<ResponseRegisterDevice>>()
    var eventnotificationList = MutableLiveData<RestResponse<ResponseNotificationListing>>()


    fun registerDeviceForNotification() {

        if (isUserLoggedIn() && !repository.getIfNotificationSentToServer()) {

            repository.registerDeviceForNotification(APIResponseCallback {

                when (it?.status) {
                    RestResponse.Status.LOADING -> {
                    }

                    RestResponse.Status.ERROR -> {
                    }

                    RestResponse.Status.SUCCESS -> {
                        repository.saveNotificationSentToServer(true)
                    }
                }

            })
        }

    }


    fun unRegisterDeviceForNotification() {

        eventUnRegisterDevice.postValue(RestResponse())

        repository.unRegisterDeviceForNotification(APIResponseCallback {

            eventUnRegisterDevice.postValue(it)

        })
    }


    fun getNotificationList() {
        eventnotificationList.postValue(RestResponse())

        repository.callAPINotificationList(
            APIResponseCallback {
                eventnotificationList.postValue(it)
            })
    }


}