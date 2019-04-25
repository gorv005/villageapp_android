package com.villageapp.models.home

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
data class Restaurant @ParcelConstructor constructor(
    val address: String?,
    val id: Int?,
    val image: String?,
    var is_meal_consumed: Boolean = false,
    val name: String?,
    val phone: String?,
    val points: Int?,
    var adapterPosition: Int?
)