package com.villageapp.callbacks

import com.villageapp.models.signUp.ResponseSignUp
import com.villageapp.models.signin.ResponseSignIn

interface SignUpFragmentInterface {
    fun onSignUpSuccessful(response: ResponseSignUp?)
    fun onSignInSuccessful(response: ResponseSignIn?)

    fun setSignInFragment()
    fun setSignUpFragment()

    fun onSkipClick()
}