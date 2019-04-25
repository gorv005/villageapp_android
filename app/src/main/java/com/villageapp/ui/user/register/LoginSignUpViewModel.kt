package com.villageapp.ui.user.register

import android.arch.lifecycle.MutableLiveData
import com.villageapp.base.BaseViewModel
import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.models.signUp.PayLoadSignIn
import com.villageapp.models.signUp.ResponseSignUp
import com.villageapp.models.signUp.SignupRequestPayload
import com.villageapp.models.signUp.facebook.PayLoadFacebookLogin
import com.villageapp.models.signin.ResponseSignIn
import com.villageapp.network.RestResponse

class LoginSignUpViewModel(repository: LoginSignUpRepository) : BaseViewModel<LoginSignUpRepository>(repository) {

    var eventSignUpUser = MutableLiveData<RestResponse<ResponseSignUp>>()
    var eventSignInUser = MutableLiveData<RestResponse<ResponseSignIn>>()
    var eventFBSignInUser = MutableLiveData<RestResponse<ResponseSignIn>>()


    fun signUpUser(email: String, name: String?, password: String) {

        val payLoadSignUpUser = SignupRequestPayload(
            email, name, password
        )
        eventSignUpUser.postValue(RestResponse())

        repository.signUpUser(payLoadSignUpUser, APIResponseCallback {
            eventSignUpUser.postValue(it)
        })
    }

    fun signInUser(
        email: String,
        grant_type: String,
        password: String,
        rememberMe: Boolean
    ) {

        val payLoadSignIn = PayLoadSignIn(
            email, grant_type, password,rememberMe
        )
        eventSignInUser.postValue(RestResponse())

        repository.signInUser(payLoadSignIn, APIResponseCallback {
            eventSignInUser.postValue(it)
        })
    }


    fun facebookLogin(
        provider: String,
        assertion: String,
        grant_type: String
    ) {

        val payLoadFacebookLogin = PayLoadFacebookLogin(
            provider, assertion, grant_type)

        eventFBSignInUser.postValue(RestResponse())

        repository.fbSignInUser(payLoadFacebookLogin, APIResponseCallback {
            eventFBSignInUser.postValue(it)
        })
    }

}