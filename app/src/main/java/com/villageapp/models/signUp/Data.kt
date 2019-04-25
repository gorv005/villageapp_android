package com.villageapp.models.signUp

import com.villageapp.models.user.info.get.User

data class Data(
    val access_token: String?,
    val created_at: String?,
    val result: User?,
    val token_type: String?
)