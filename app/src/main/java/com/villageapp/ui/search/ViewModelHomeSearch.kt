package com.villageapp.ui.search

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.home.search.ResponseHomeSearch
import com.villageapp.network.RestResponse

class ViewModelHomeSearch(repository: HomeSearchListRepository) : BaseViewModel<HomeSearchListRepository>(repository) {

    var eventHomeSearch = MutableLiveData<RestResponse<ResponseHomeSearch>>()


    fun getHomeSearched(searchedQuery: String) {
        eventHomeSearch.postValue(RestResponse())

        repository.getHomeSearchedList(searchedQuery, APIResponseCallback<ResponseHomeSearch> {
            eventHomeSearch.postValue(it)

        })
    }

}