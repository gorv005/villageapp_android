package com.villageapp.models.signUp.facebook

data class PayLoadFacebookLogin(
    val provider: String,
    val assertion: String,
    val grant_type: String
)