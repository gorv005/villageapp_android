package com.villageapp.ui.user.register

import com.villageapp.base.BaseRepository
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.signUp.PayLoadSignIn
import com.villageapp.models.signUp.ResponseSignUp
import com.villageapp.models.signUp.SignupRequestPayload
import com.villageapp.models.signUp.facebook.PayLoadFacebookLogin
import com.villageapp.models.signin.ResponseSignIn


class LoginSignUpRepository : BaseRepository() {

    fun signUpUser(signupRequestPayload: SignupRequestPayload, callback: APIResponseCallback<ResponseSignUp>) {
        sendApiCall(appRestService.signUp(signupRequestPayload), callback)
    }

    fun signInUser(payLoadSignIn: PayLoadSignIn, callback: APIResponseCallback<ResponseSignIn>) {
        sendApiCall(appRestService.login(payLoadSignIn), callback)
    }


    fun fbSignInUser(payLoadFacebookLogin: PayLoadFacebookLogin, callback: APIResponseCallback<ResponseSignIn>) {
        sendApiCall(appRestService.fbLogin(payLoadFacebookLogin), callback)
    }

     fun saveLoggedInUserDataToPref(data: ResponseSignUp) {

         data.data?.access_token?.let { preferenceManager.loginUser(it, data.data.result) }

    }

    fun saveLoggedInUserDataToPref(data: ResponseSignIn) {

        data.access_token?.let { preferenceManager.loginUser(it, data.result) }

    }
}