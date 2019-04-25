package com.villageapp.network


import com.villageapp.managers.PreferenceManager
import com.villageapp.models.home.search.ResponseHomeSearch
import com.villageapp.models.dailyalerts.ResponseMarkReadDailyAlert
import com.villageapp.models.dailyalerts.detail.ResponseDailyAlertDetail
import com.villageapp.models.dailyalerts.list.ResponseGetDailyAlertList
import com.villageapp.models.home.HomeResponseModal
import com.villageapp.models.meal.consume.inrestaurant.EarnedPoints
import com.villageapp.models.meal.consume.inrestaurant.PayLoadMarkReadOrConsume
import com.villageapp.models.meal.consume.inrestaurant.ResponseMarkMealConsumedInRestaurant
import com.villageapp.models.meal.consume.list.MealConsumedIds
import com.villageapp.models.meal.consume.list.PayLoadMealConsumedList
import com.villageapp.models.meal.consume.list.ResponseMealConsumedList
import com.villageapp.models.meal.search.ResponseMealSearchList
import com.villageapp.models.notification.PayloadDeviceRegistration
import com.villageapp.models.notification.ResponseNotificationListing
import com.villageapp.models.notification.ResponseRegisterDevice
import com.villageapp.models.product.detail.ResponseModalProductDetail
import com.villageapp.models.product.list.ResponseProductList
import com.villageapp.models.product.marksave.ResponseMarkFavourite
import com.villageapp.models.signUp.PayLoadSignIn
import com.villageapp.models.signUp.ResponseSignUp
import com.villageapp.models.signUp.SignUpRequestPayloadContainer
import com.villageapp.models.signUp.SignupRequestPayload
import com.villageapp.models.signUp.facebook.PayLoadFacebookLogin
import com.villageapp.models.signin.ResponseSignIn
import com.villageapp.models.user.favouritelist.ResponseUserFavouriteList
import com.villageapp.models.user.info.get.ResponseUserInfo
import com.villageapp.models.user.info.update.ResponseUpdateUserProfile
import com.villageapp.models.user.logout.PayLoadLogout
import com.villageapp.models.user.logout.ResponseLogout
import com.villageapp.models.user.password.change.PayLoadChangePassword
import com.villageapp.models.user.password.change.ResponseChangePassword
import com.villageapp.models.user.password.forgot.PayLoadForgotPassword
import com.villageapp.models.user.password.forgot.ResponseForgotPassword
import com.villageapp.models.user.password.reset.PayLoadResetPassword
import com.villageapp.models.user.password.reset.ResponseResetPassword
import com.villageapp.models.user.remove.image.ResponseRemoveUserImage
import com.villageapp.models.user.remove.profile.ResponseDeleteUserProfile
import com.villageapp.utils.Config
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


/**
 * Signature of all the rest services will be here.
 */
class AppRestService(
    internal val appRestApiFast: AppRestApiFast,
    internal val preferenceManager: PreferenceManager
) {

    private fun getBearerToken(): String {
        val token = preferenceManager.getStringPreference(Config.SharedPreferences.PROPERTY_JWT_TOKEN)
        return "Bearer $token"
    }

    private fun getToken(): String {
        return preferenceManager.getStringPreference(Config.SharedPreferences.PROPERTY_JWT_TOKEN) ?: ""
    }


    /*Authentication*/

    fun signUp(signupRequestPayload: SignupRequestPayload): Observable<Response<ResponseSignUp>> {
        val apiCall = appRestApiFast.signUp(SignUpRequestPayloadContainer(signupRequestPayload))
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(payLoadSignIn: PayLoadSignIn): Observable<Response<ResponseSignIn>> {
        val apiCall = appRestApiFast.login(payLoadSignIn)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun fbLogin(payLoadFacebookLogin: PayLoadFacebookLogin): Observable<Response<ResponseSignIn>> {
        val apiCall = appRestApiFast.fbLogin(payLoadFacebookLogin)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    /*USER*/

    fun getUserInfo(userId: Int): Observable<Response<ResponseUserInfo>> {
        val apiCall = appRestApiFast.getUserInfo(getBearerToken(), userId)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateUserProfile(
        userId: Int,
        nameRequestBody: RequestBody,
        emailRequestBody: RequestBody,
        imageRequestBody: MultipartBody.Part?
    ): Observable<Response<ResponseUpdateUserProfile>> {
        val apiCall =
            appRestApiFast.updateUserProfile(
                getBearerToken(),
                userId,
                nameRequestBody,
                emailRequestBody,
                imageRequestBody
            )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun removeUserImage(userId: Int): Observable<Response<ResponseRemoveUserImage>> {
        val apiCall = appRestApiFast.removeUserImage(getBearerToken(), userId)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteUserProfile(userId: Int): Observable<Response<ResponseDeleteUserProfile>> {
        val apiCall = appRestApiFast.deleteUserProfile(getBearerToken(), userId)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun logout(): Observable<Response<ResponseLogout>> {
        val apiCall = appRestApiFast.logout(PayLoadLogout(getToken()))
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    /*Password*/

    fun resetPassword(payLoad: PayLoadResetPassword): Observable<Response<ResponseResetPassword>> {
        val apiCall = appRestApiFast.resetPassword(payLoad)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun changePassword(payLoad: PayLoadChangePassword): Observable<Response<ResponseChangePassword>> {
        val apiCall = appRestApiFast.changePassword(getBearerToken(),payLoad)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun forgotPassword(payLoadLogout: PayLoadForgotPassword): Observable<Response<ResponseForgotPassword>> {
        val apiCall = appRestApiFast.forgotPassword(payLoadLogout)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    /*Home Feed*/
    fun getHomeFeeds(): Observable<Response<HomeResponseModal>> {
        val apiCall = appRestApiFast.getHomeFeeds(getBearerToken())
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    /*Product*/

    fun getProductsFromCategoryId(categoryId: Int): Observable<Response<ResponseProductList>> {
        val apiCall = appRestApiFast.getProductListFromCategory(getBearerToken(), categoryId)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getProductsDetail(productId: Int): Observable<Response<ResponseModalProductDetail>> {
        val apiCall = appRestApiFast.getProductDetails(getBearerToken(), productId)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun markProductFavourite(productId: Int): Observable<Response<ResponseMarkFavourite>> {
        val apiCall = appRestApiFast.markProductFavourite(getBearerToken(), productId)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun removeProductFavourite(productId: Int): Observable<Response<ResponseMarkFavourite>> {
        val apiCall = appRestApiFast.removeProductFavourite(getBearerToken(), productId)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFavouriteList(): Observable<Response<ResponseUserFavouriteList>> {
        val apiCall = appRestApiFast.getFavouriteList(getBearerToken())
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getProductListWithSearch(
        perPageCount: Int,
        pageCount: Int,
        query: String
    ): Observable<Response<ResponseProductList>> {
        val apiCall = appRestApiFast.getProductListWithSearch(getBearerToken(), perPageCount, pageCount, query)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    /*Meal*/

    fun markMealConsumed(restaurantId: Int): Observable<Response<ResponseMarkMealConsumedInRestaurant>> {
        val apiCall = appRestApiFast.markMealConsumed(
            getBearerToken(),
            restaurantId,
            PayLoadMarkReadOrConsume(EarnedPoints(Config.ActionMarkReadConsumed.MEAL_CONSUMED))
        )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchMeal(query: String?): Observable<Response<ResponseMealSearchList>> {
        val apiCall = appRestApiFast.searchMeal(
            getBearerToken(),
            query
        )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun postMealConsumedList(mealIds: String): Observable<Response<ResponseMealConsumedList>> {
        val apiCall = appRestApiFast.postMealConsumedList(
            getBearerToken(),
            PayLoadMealConsumedList(MealConsumedIds(mealIds))
        )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun getDailyAlertList(): Observable<Response<ResponseGetDailyAlertList>> {
        val apiCall = appRestApiFast.getDailyAlertList(
            getBearerToken())
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun getDailyAlertDetail( alertId: Int): Observable<Response<ResponseDailyAlertDetail>> {
        val apiCall = appRestApiFast.getDailyAlertDetail(
            getBearerToken(),
            alertId)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun markDailyAlertRead(alertId: Int): Observable<Response<ResponseMarkReadDailyAlert>> {
        val apiCall = appRestApiFast.markAsReadDailyAlert(
            getBearerToken(),
            alertId,
            PayLoadMarkReadOrConsume(EarnedPoints(Config.ActionMarkReadConsumed.MARK_AS_READ))
        )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun markDailyAlertFavourite(alertId: Int): Observable<Response<ResponseMarkFavourite>> {
        val apiCall = appRestApiFast.markDailyAlertFavourite(
            getBearerToken(),
            alertId
        )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun removeDailyAlertFavourite(alertId: Int): Observable<Response<ResponseMarkFavourite>> {
        val apiCall = appRestApiFast.removeDailyAlertFavourite(
            getBearerToken(),
            alertId
        )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun homeSearchApi(searchedQuery: String): Observable<Response<ResponseHomeSearch>> {
        val apiCall = appRestApiFast.searchHome(
            getBearerToken(),
            searchedQuery
        )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /*Notification*/

    fun registerDeviceForNotification(device_registration_id: String): Observable<Response<ResponseRegisterDevice>> {
        val apiCall = appRestApiFast.registerDeviceForNotification(
            getBearerToken(),
            PayloadDeviceRegistration(device_registration_id)
        )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun unRegisterDeviceForNotification(device_registration_id: String): Observable<Response<ResponseRegisterDevice>> {
        val apiCall = appRestApiFast.removeDeviceFromBackEnd(
            getBearerToken(),
            device_registration_id
        )
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNotificationList(): Observable<Response<ResponseNotificationListing>> {
        val apiCall = appRestApiFast.getNotificationList(
            getBearerToken())
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
