package com.villageapp.ui.home

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.meal.search.ResponseMealSearchList
import com.villageapp.models.meal.consume.inrestaurant.ResponseMarkMealConsumedInRestaurant
import com.villageapp.models.meal.consume.list.ResponseMealConsumedList

class RepositoryMeal : BaseRepository() {

    fun callMealConsumedApi(
        restaurantId: Int,
        callback: APIResponseCallback<ResponseMarkMealConsumedInRestaurant>
    ) {
        sendApiCall(appRestService.markMealConsumed(restaurantId), callback)
    }


    fun callSearchMealList(
        query: String?,
        callback: APIResponseCallback<ResponseMealSearchList>
    ) {
        sendApiCall(appRestService.searchMeal(query), callback)
    }



    fun postConsumedMealIds(
        mealIds: String,
        callback: APIResponseCallback<ResponseMealConsumedList>
    ) {
        sendApiCall(appRestService.postMealConsumedList(mealIds), callback)
    }
}