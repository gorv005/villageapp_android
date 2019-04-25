package com.villageapp.ui.productdetail

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.product.detail.ResponseModalProductDetail

class ProductDetailRepository : BaseRepository() {

    fun getProductDetail(
        productId: Int,
        callback: APIResponseCallback<ResponseModalProductDetail>
    ) {
        sendApiCall(appRestService.getProductsDetail(productId), callback)
    }
}