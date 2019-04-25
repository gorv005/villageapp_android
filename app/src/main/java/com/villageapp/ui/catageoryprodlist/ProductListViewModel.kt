package com.villageapp.ui.catageoryprodlist

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.product.marksave.ResponseMarkFavourite
import com.villageapp.models.product.list.ResponseProductList
import com.villageapp.network.RestResponse

class ProductListViewModel(repository: ProductListRepository) : BaseViewModel<ProductListRepository>(repository) {

    var events = MutableLiveData<RestResponse<ResponseProductList>>()
    var eventsMarkSaved = MutableLiveData<RestResponse<ResponseMarkFavourite>>()
    var eventsRemoveSavedProduct = MutableLiveData<RestResponse<ResponseMarkFavourite>>()


    fun getProductListFromCatageory(catageoryId: Int) {
        events.postValue(RestResponse())

        repository.getProductListFronCatageoryId(catageoryId, APIResponseCallback {
            events.postValue(it)
        })
    }


    fun marProductSaved(productId: Int) {
        eventsMarkSaved.postValue(RestResponse())

        repository.markedProductSaved(productId, APIResponseCallback {
            eventsMarkSaved.postValue(it)
        })
    }


    fun removeProductSaved(productId: Int) {
        eventsRemoveSavedProduct.postValue(RestResponse())

        repository.markedProductUnSaved(productId, APIResponseCallback {
            eventsRemoveSavedProduct.postValue(it)
        })
    }


}