package com.villageapp.base

import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.managers.PreferenceManager
import com.villageapp.network.APICallAndResponseHelper
import com.villageapp.network.AppRestService
import com.villageapp.utils.Config.SharedPreferences.PROPERTY_IS_ON_BOARDING_SEEN
import com.villageapp.utils.Config.SharedPreferences.PROPERTY_USER_ID
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Response

open class BaseRepository : KoinComponent {

    internal val appRestService: AppRestService by inject()
    internal val preferenceManager: PreferenceManager by inject()

    private var compositeDisposable = CompositeDisposable()

    fun onCleared() {
        compositeDisposable.clear()
    }

    fun <T> sendApiCall(observable: Observable<Response<T>>, callback: APIResponseCallback<T>?) {
        APICallAndResponseHelper.call(observable, callback, compositeDisposable)
    }

    fun isUserLoggedIn() = preferenceManager.isUserLoggedIn()
    fun isUserFBLoggedIn() = preferenceManager.isUserFBLoggedIn()
    fun isOnBoardingSeen() = preferenceManager.isOnBoardingSeen()

    fun getLoggedInUserId() = preferenceManager.getIntPreference(PROPERTY_USER_ID)
    fun logOutLocal() {
        preferenceManager.logOut()
    }

    fun setPrefOnBoardingSeen(isOnBoardingSeen: Boolean) {

        preferenceManager.savePreference(PROPERTY_IS_ON_BOARDING_SEEN, isOnBoardingSeen)
    }
}