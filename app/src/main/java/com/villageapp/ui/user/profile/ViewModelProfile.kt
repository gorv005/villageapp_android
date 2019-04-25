package com.villageapp.ui.user.profile

import android.arch.lifecycle.MutableLiveData
import android.net.Uri
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.user.info.get.ResponseUserInfo
import com.villageapp.models.user.info.get.User
import com.villageapp.models.user.info.update.ResponseUpdateUserProfile
import com.villageapp.models.user.logout.ResponseLogout
import com.villageapp.models.user.remove.image.ResponseRemoveUserImage
import com.villageapp.models.user.remove.profile.ResponseDeleteUserProfile
import com.villageapp.network.RestResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ViewModelProfile(repository: RepositoryProfile) : BaseViewModel<RepositoryProfile>(repository) {

    var eventGetUserInfo = MutableLiveData<RestResponse<ResponseUserInfo>>()
    var eventUpdateUserInfo = MutableLiveData<RestResponse<ResponseUpdateUserProfile>>()
    var eventRemoveUserImage = MutableLiveData<RestResponse<ResponseRemoveUserImage>>()
    var eventDeleteUserProfile = MutableLiveData<RestResponse<ResponseDeleteUserProfile>>()
    var eventLogoutUserServer = MutableLiveData<RestResponse<ResponseLogout>>()

    private var mediaTypeTextPlain = "text/plain"

    fun getUserInfo() {
        eventGetUserInfo.postValue(RestResponse())

        repository.getUserInfo(APIResponseCallback {

            eventGetUserInfo.postValue(it)

        })
    }

    fun updateUserProfileAPI(name: String, email: String, selectedImageUriPath: String?) {

        val nameBody = RequestBody.create(MediaType.parse(mediaTypeTextPlain), name)
        val emailBody = RequestBody.create(MediaType.parse(mediaTypeTextPlain), email)
        var photo: MultipartBody.Part? = null

        if (selectedImageUriPath != null) {


            try {
                val imageUri = Uri.parse(selectedImageUriPath)
                val file = File(imageUri.path)
                val fileName = file.name

                val contentType = MediaType.parse("image/*")

                val requestFile = RequestBody.create(
                    contentType,
                    file
                )

                photo = MultipartBody.Part.createFormData("user[image]", fileName, requestFile)


            } catch (e: Exception) {
            }

        }

        eventUpdateUserInfo.postValue(RestResponse())



        repository.updateUserInfo(nameBody, emailBody, photo, APIResponseCallback {

            eventUpdateUserInfo.postValue(it)

        })
    }

    fun updateUserInfoInPref(user: User) {

        repository.preferenceManager.saveUserData(user)


    }

    fun removeUserImage() {

        eventRemoveUserImage.postValue(RestResponse())

        repository.removeUserImage(APIResponseCallback {

            eventRemoveUserImage.postValue(it)

        })
    }


    fun deleteUserProfile() {

        eventDeleteUserProfile.postValue(RestResponse())

        repository.deleteUserProfile(APIResponseCallback {

            eventDeleteUserProfile.postValue(it)

        })
    }

    fun logoutFromServer() {

        eventLogoutUserServer.postValue(RestResponse())

        repository.logoutUserAPI(APIResponseCallback {

            eventLogoutUserServer.postValue(it)

        })
    }

    fun setEarnedPoints(earnedPoints: Int) {
        repository.preferenceManager.saveEarnedPoints(earnedPoints)

    }

    fun getUserImage(): String? {

        return repository.preferenceManager.getLoggedInUserImage()
    }

    fun removeUserImagePref() {

        repository.preferenceManager.saveUserImage(null)
    }

    fun getUserName(): String? {
        return repository.preferenceManager.getLoggedInUserName()

    }

    fun getUserEmail(): String? {
        return repository.preferenceManager.getLoggedInUserEmail()

    }

    fun getEarnedPoints(): Int? {
        return repository.preferenceManager.getUserEarnedPoints()

    }

    fun logoutPref() {
        repository.preferenceManager.logOut()
    }


}