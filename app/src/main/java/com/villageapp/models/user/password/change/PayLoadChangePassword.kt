package com.villageapp.models.user.password.change

data class PayLoadChangePassword(
    val old_password: String,
    val password: String,
    val confirm_password: String
)