package com.villageapp.network


import com.villageapp.models.home.search.ResponseHomeSearch
import com.villageapp.models.dailyalerts.ResponseMarkReadDailyAlert
import com.villageapp.models.dailyalerts.detail.ResponseDailyAlertDetail
import com.villageapp.models.dailyalerts.list.ResponseGetDailyAlertList
import com.villageapp.models.home.HomeResponseModal
import com.villageapp.models.meal.consume.inrestaurant.PayLoadMarkReadOrConsume
import com.villageapp.models.meal.consume.inrestaurant.ResponseMarkMealConsumedInRestaurant
import com.villageapp.models.meal.consume.list.PayLoadMealConsumedList
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface AppRestApiFast {


    /*Authentication*/

    @POST(Config.Endpoints.SIGN_UP_API)
    fun signUp(
        @Body data: SignUpRequestPayloadContainer
    ): Observable<Response<ResponseSignUp>>


    @POST(Config.Endpoints.LOGIN_API)
    fun login(
        @Body data: PayLoadSignIn
    ): Observable<Response<ResponseSignIn>>


    @POST(Config.Endpoints.LOGIN_API)
    fun fbLogin(
        @Body data: PayLoadFacebookLogin
    ): Observable<Response<ResponseSignIn>>


    /*USER*/

    @POST(Config.Endpoints.REMOVE_USER_IMAGE)
    fun removeUserImage(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Observable<Response<ResponseRemoveUserImage>>


    @DELETE(Config.Endpoints.DELETE_USER_PROFILE)
    fun deleteUserProfile(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Observable<Response<ResponseDeleteUserProfile>>


    @Multipart
    @PUT(Config.Endpoints.UPDATE_USER_PROFILE)
    fun updateUserProfile(
        @Header("Authorization") token: String
        , @Path("userId") userId: Int
        , @Part("user[name]") name: RequestBody
        , @Part("user[email]") email: RequestBody
        , @Part file: MultipartBody.Part?
    ): Observable<Response<ResponseUpdateUserProfile>>


    @GET(Config.Endpoints.USER_INFO)
    fun getUserInfo(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Observable<Response<ResponseUserInfo>>


    @POST(Config.Endpoints.LOGOUT)
    fun logout(
        @Body data: PayLoadLogout
    ): Observable<Response<ResponseLogout>>


    /*Password*/

    @POST(Config.Endpoints.FORGOT_PASSWORD)
    fun forgotPassword(
        @Body data: PayLoadForgotPassword
    ): Observable<Response<ResponseForgotPassword>>


    @POST(Config.Endpoints.RESET_PASSWORD)
    fun resetPassword(
        @Body data: PayLoadResetPassword
    ): Observable<Response<ResponseResetPassword>>


    @POST(Config.Endpoints.CHANGE_PASSWORD)
    fun changePassword(
        @Header("Authorization") token: String,
        @Body data: PayLoadChangePassword
    ): Observable<Response<ResponseChangePassword>>


    /*Home Feed*/

    @GET(Config.Endpoints.HOME_FEEDS)
    fun getHomeFeeds(
        @Header("Authorization") token: String
    ): Observable<Response<HomeResponseModal>>


    /*Product*/

    @POST(Config.Endpoints.PRODUCT_MARK_FAVOURITE)
    fun markProductFavourite(
        @Header("Authorization") token: String,
        @Path("productId") productId: Int
    ): Observable<Response<ResponseMarkFavourite>>


    @DELETE(Config.Endpoints.PRODUCT_REMOVE_FAVOURITE)
    fun removeProductFavourite(
        @Header("Authorization") token: String,
        @Path("productId") productId: Int
    ): Observable<Response<ResponseMarkFavourite>>


    @GET(Config.Endpoints.LIST_FAVORITES)
    fun getFavouriteList(
        @Header("Authorization") token: String
    ): Observable<Response<ResponseUserFavouriteList>>

    @GET(Config.Endpoints.PRODUCT_LIST_FROM_CATEGORY)
    fun getProductListFromCategory(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Int
    ): Observable<Response<ResponseProductList>>


    @GET(Config.Endpoints.PRODUCT_DETAIL)
    fun getProductDetails(
        @Header("Authorization") token: String,
        @Path("productId") productId: Int
    ): Observable<Response<ResponseModalProductDetail>>


    @GET(Config.Endpoints.PRODUCT_LIST_WITH_SEARCH)
    fun getProductListWithSearch(
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("search") searchTerm: String
    ): Observable<Response<ResponseProductList>>


    /*Meal*/


    @POST(Config.Endpoints.MARK_MEAL_CONSUMED)
    fun markMealConsumed(
        @Header("Authorization") token: String,
        @Path("restaurantId") restaurantId: Int,
        @Body payLoad: PayLoadMarkReadOrConsume
    ): Observable<Response<ResponseMarkMealConsumedInRestaurant>>


    @GET(Config.Endpoints.MEAL_SEARCH_LIST)
    fun searchMeal(
        @Header("Authorization") token: String,
        @Query("search") query: String?
    ): Observable<Response<ResponseMealSearchList>>

    @POST(Config.Endpoints.MEAL_CONSUMED_LIST)
    fun postMealConsumedList(
        @Header("Authorization") token: String,
        @Body payLoad: PayLoadMealConsumedList
    ): Observable<Response<com.villageapp.models.meal.consume.list.ResponseMealConsumedList>>


    /*Daily Alert*/

    @GET(Config.Endpoints.GET_DAILY_ALERTS)
    fun getDailyAlertList(
        @Header("Authorization") token: String
    )
            : Observable<Response<ResponseGetDailyAlertList>>


    @GET(Config.Endpoints.GET_DAILY_ALERTS_DETAIL)
    fun getDailyAlertDetail(
        @Header("Authorization") token: String,
        @Path("alertId") alertId: Int
    )
            : Observable<Response<ResponseDailyAlertDetail>>


    @POST(Config.Endpoints.GET_DAILY_ALERTS_MARK_READ)
    fun markAsReadDailyAlert(
        @Header("Authorization") token: String,
        @Path("alertId") alertId: Int,
        @Body payLoad: PayLoadMarkReadOrConsume
    ): Observable<Response<ResponseMarkReadDailyAlert>>


    @POST(Config.Endpoints.DAILY_ALERT_MARK_FAVOURITE)
    fun markDailyAlertFavourite(
        @Header("Authorization") token: String,
        @Path("alertId") alertId: Int
    ): Observable<Response<ResponseMarkFavourite>>


    @DELETE(Config.Endpoints.DAILY_ALERT_REMOVE_FAVOURITE)
    fun removeDailyAlertFavourite(
        @Header("Authorization") token: String,
        @Path("alertId") alertId: Int
    ): Observable<Response<ResponseMarkFavourite>>


    /*Home Search*/


    @GET(Config.Endpoints.HOME_SEARCH)
    fun searchHome(
        @Header("Authorization") token: String,
        @Query("keyword") searchTerm: String
    ): Observable<Response<ResponseHomeSearch>>



    /*Notification*/

    @POST(Config.Endpoints.NOTIFICATION_REGISTER_MOBILE_DEVICE)
    fun registerDeviceForNotification(
        @Header("Authorization") token: String,
        @Body deviceRegistrationPayload: PayloadDeviceRegistration
    ): Observable<Response<ResponseRegisterDevice>>


    @DELETE(Config.Endpoints.NOTIFICATION_UNREGISTER_MOBILE_DEVICE)
    fun removeDeviceFromBackEnd(
        @Header("Authorization") token: String,
        @Path("deviceId") deviceId: String
    ): Observable<Response<ResponseRegisterDevice>>

    @GET(Config.Endpoints.NOTIFICATION_LISTING)
    fun getNotificationList(
        @Header("Authorization") token: String
    ): Observable<Response<ResponseNotificationListing>>
}

