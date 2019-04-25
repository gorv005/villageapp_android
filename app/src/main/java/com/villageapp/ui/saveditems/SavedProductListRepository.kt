package com.villageapp.ui.saveditems

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.product.list.ResponseProductList
import com.villageapp.models.user.favouritelist.ResponseUserFavouriteList

class SavedProductListRepository : BaseRepository() {

    fun getSavedList(
        callback: APIResponseCallback<ResponseUserFavouriteList>
    ) {
        sendApiCall(appRestService.getFavouriteList(), callback)
    }
}