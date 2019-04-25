package com.villageapp.ui.user.profile

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.user.info.get.ResponseUserInfo
import com.villageapp.models.user.info.update.ResponseUpdateUserProfile
import com.villageapp.models.user.logout.ResponseLogout
import com.villageapp.models.user.remove.image.ResponseRemoveUserImage
import com.villageapp.models.user.remove.profile.ResponseDeleteUserProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RepositoryProfile : BaseRepository() {

    fun getUserInfo(callback: APIResponseCallback<ResponseUserInfo>) {
        sendApiCall(appRestService.getUserInfo(preferenceManager.getLoggedInUserId()), callback)
    }

    fun updateUserInfo(
        name: RequestBody,
        email: RequestBody,
        photo: MultipartBody.Part?,
        callback: APIResponseCallback<ResponseUpdateUserProfile>
    ) {
        sendApiCall(
            appRestService.updateUserProfile(preferenceManager.getLoggedInUserId(), name, email, photo),
            callback
        )
    }


    fun removeUserImage(callback: APIResponseCallback<ResponseRemoveUserImage>) {
        sendApiCall(appRestService.removeUserImage(preferenceManager.getLoggedInUserId()), callback)
    }


    fun deleteUserProfile(callback: APIResponseCallback<ResponseDeleteUserProfile>) {
        sendApiCall(appRestService.deleteUserProfile(preferenceManager.getLoggedInUserId()), callback)
    }

    fun logoutUserAPI(callback: APIResponseCallback<ResponseLogout>) {
        sendApiCall(appRestService.logout(), callback)
    }

}
