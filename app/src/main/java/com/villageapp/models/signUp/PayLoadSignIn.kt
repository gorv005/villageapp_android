package com.villageapp.models.signUp

data class PayLoadSignIn(
    val email: String?,
    val grant_type: String?,
    val password: String?,
    val remember_me: Boolean = false
)