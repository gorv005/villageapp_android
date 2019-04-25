package com.villageapp.ui.home

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.home.HomeResponseModal
import com.villageapp.network.RestResponse

class HomeViewModel(repository: HomeRepository) : BaseViewModel<HomeRepository>(repository) {

    var events = MutableLiveData<RestResponse<HomeResponseModal>>()


    fun getHomeFeed() {
        events.postValue(RestResponse())

        repository.getHomeFeed(APIResponseCallback {
            events.postValue(it)
        })
    }

}