package com.villageapp.ui.saveditems

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.product.list.ResponseProductList
import com.villageapp.models.user.favouritelist.ResponseUserFavouriteList
import com.villageapp.network.RestResponse
import com.villageapp.ui.catageoryprodlist.ProductListRepository

class SavedProductListViewModel(repository: SavedProductListRepository) : BaseViewModel<SavedProductListRepository>(repository){

    var events = MutableLiveData<RestResponse<ResponseUserFavouriteList>>()


    fun getSavedProdDailyAlertList() {
        events.postValue(RestResponse())

        repository.getSavedList(APIResponseCallback {
            events.postValue(it)
        })
    }

}