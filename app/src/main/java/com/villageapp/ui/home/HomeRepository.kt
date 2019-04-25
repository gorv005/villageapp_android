package com.villageapp.ui.home

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.home.HomeResponseModal

class HomeRepository : BaseRepository() {

    fun getHomeFeed(callback: APIResponseCallback<HomeResponseModal>) {
        sendApiCall(appRestService.getHomeFeeds(), callback)
    }
}