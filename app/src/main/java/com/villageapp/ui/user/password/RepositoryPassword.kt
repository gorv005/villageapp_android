package com.villageapp.ui.user.password

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.user.password.change.PayLoadChangePassword
import com.villageapp.models.user.password.change.ResponseChangePassword
import com.villageapp.models.user.password.forgot.PayLoadForgotPassword
import com.villageapp.models.user.password.forgot.ResponseForgotPassword
import com.villageapp.models.user.password.reset.PayLoadResetPassword
import com.villageapp.models.user.password.reset.PayLoadUser
import com.villageapp.models.user.password.reset.ResponseResetPassword

class RepositoryPassword : BaseRepository() {

    fun forgotPassword(email: String, callback: APIResponseCallback<ResponseForgotPassword>) {

        val payLoadForgotPassword = PayLoadForgotPassword(email)
        sendApiCall(appRestService.forgotPassword(payLoadForgotPassword), callback)
    }


    fun resetPassword(
        password: String,
        password_confirmation: String,
        reset_password_token: String,
        callback: APIResponseCallback<ResponseResetPassword>
    ) {

        val payLoadUser = PayLoadUser(password, password_confirmation, reset_password_token)

        sendApiCall(appRestService.resetPassword(PayLoadResetPassword(payLoadUser)), callback)
    }


    fun changePassword(
         old_password: String,
         password: String,
         confirm_password: String,
         callback: APIResponseCallback<ResponseChangePassword>
    ) {


        sendApiCall(appRestService.changePassword(PayLoadChangePassword(old_password, password, confirm_password)), callback)
    }
}