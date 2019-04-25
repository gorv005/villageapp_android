package com.villageapp.ui.search

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.home.search.ResponseHomeSearch
import com.villageapp.models.user.favouritelist.ResponseUserFavouriteList

class HomeSearchListRepository : BaseRepository() {

    fun getHomeSearchedList(
        searchedQuery: String, callback: APIResponseCallback<ResponseHomeSearch>
    ) {
        sendApiCall(appRestService.homeSearchApi(searchedQuery), callback)
    }
}