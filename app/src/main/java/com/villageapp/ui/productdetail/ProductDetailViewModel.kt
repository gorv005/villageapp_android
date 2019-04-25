package com.villageapp.ui.productdetail

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.product.detail.ResponseModalProductDetail
import com.villageapp.network.RestResponse

class ProductDetailViewModel(repository: ProductDetailRepository) : BaseViewModel<ProductDetailRepository>(repository){

    var events = MutableLiveData<RestResponse<ResponseModalProductDetail>>()


    fun getProductDetail(productId: Int) {
        events.postValue(RestResponse())

        repository.getProductDetail(productId, APIResponseCallback {
            events.postValue(it)
        })
    }

}