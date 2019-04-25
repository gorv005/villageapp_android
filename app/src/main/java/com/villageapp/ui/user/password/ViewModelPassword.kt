package com.villageapp.ui.user.password

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.user.password.change.ResponseChangePassword
import com.villageapp.models.user.password.forgot.ResponseForgotPassword
import com.villageapp.models.user.password.reset.ResponseResetPassword
import com.villageapp.network.RestResponse


class ViewModelPassword(repository: RepositoryPassword) : BaseViewModel<RepositoryPassword>(repository) {

    var eventForgotPassword = MutableLiveData<RestResponse<ResponseForgotPassword>>()
    var eventResetPassword = MutableLiveData<RestResponse<ResponseResetPassword>>()
    var eventChangePassword = MutableLiveData<RestResponse<ResponseChangePassword>>()


    fun forgotPasswordSendEmail(emailStr: String) {
        eventForgotPassword.postValue(RestResponse())

        repository.forgotPassword(emailStr, APIResponseCallback {
            eventForgotPassword.postValue(it)
        })
    }

    fun resetPassword(
        password: String,
        password_confirmation: String,
        reset_password_token: String
    ) {
        eventResetPassword.postValue(RestResponse())

        repository.resetPassword(password, password_confirmation, reset_password_token, APIResponseCallback {
            eventResetPassword.postValue(it)
        })
    }

    fun changePassword(
        old_password: String,
        password: String,
        confirm_password: String
    ) {
        eventChangePassword.postValue(RestResponse())

        repository.changePassword(old_password, password, confirm_password
            , APIResponseCallback {
                eventChangePassword.postValue(it)
            })
    }

}