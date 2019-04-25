package com.villageapp.models.signin

import com.villageapp.models.user.info.get.User

data class ResponseSignIn(
    val access_token: String?,
    val created_at: Int?,
    val message: String?,
    val result: User?,
    val status_code: Int?,
    val token_type: String?
)