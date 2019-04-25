package com.villageapp.ui.catageoryprodlist

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.product.marksave.ResponseMarkFavourite
import com.villageapp.models.product.list.ResponseProductList

class ProductListRepository : BaseRepository() {

    fun getProductListFronCatageoryId(
        catageoryId: Int,
        callback: APIResponseCallback<ResponseProductList>
    ) {
        sendApiCall(appRestService.getProductsFromCategoryId(catageoryId), callback)
    }

    fun markedProductSaved(productId: Int, apiResponseCallback: APIResponseCallback<ResponseMarkFavourite>) {
        sendApiCall(appRestService.markProductFavourite(productId), apiResponseCallback)
    }


    fun markedProductUnSaved(productId: Int, apiResponseCallback: APIResponseCallback<ResponseMarkFavourite>) {
        sendApiCall(appRestService.removeProductFavourite(productId), apiResponseCallback)
    }
}