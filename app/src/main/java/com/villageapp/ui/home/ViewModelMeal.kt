package com.villageapp.ui.home

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.meal.search.ResponseMealSearchList
import com.villageapp.models.meal.consume.inrestaurant.ResponseMarkMealConsumedInRestaurant
import com.villageapp.models.meal.consume.list.ResponseMealConsumedList
import com.villageapp.network.RestResponse

class ViewModelMeal(repository: RepositoryMeal) : BaseViewModel<RepositoryMeal>(repository) {

    var events = MutableLiveData<RestResponse<ResponseMarkMealConsumedInRestaurant>>()
    var eventsMealSearch = MutableLiveData<RestResponse<ResponseMealSearchList>>()
    var eventsPostMealConsumeList = MutableLiveData<RestResponse<ResponseMealConsumedList>>()


    fun markMealConsumedInRestaurant(restaurantId: Int) {
        events.postValue(RestResponse())

        repository.callMealConsumedApi(
            restaurantId,
            APIResponseCallback {
                events.postValue(it)
            })
    }


    fun searchMealList(query: String?) {
        eventsMealSearch.postValue(RestResponse())

        repository.callSearchMealList(
            query,
            APIResponseCallback {
                eventsMealSearch.postValue(it)
            })
    }


    fun postMealConsumeList(mealIds: String) {
        eventsPostMealConsumeList.postValue(RestResponse())

        repository.postConsumedMealIds(
            mealIds,
            APIResponseCallback {
                eventsPostMealConsumeList.postValue(it)
            })
    }

}