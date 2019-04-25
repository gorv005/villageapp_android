package com.villageapp.models.user.info.get

import com.villageapp.models.signUp.UserImage
import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
data class User @ParcelConstructor constructor(
    val earned_points: Int?,
    val email: String?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val created_at: String?,
    val updated_at: String?,
    val total_points: Int?,
    val raw_reset_password_token: String?,
    var isFBLogin: Boolean = false
)