package com.villageapp.models.user.password.reset

data class PayLoadUser(
    val password: String,
    val password_confirmation: String,
    val reset_password_token: String
)