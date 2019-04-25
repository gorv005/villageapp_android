package com.villageapp.models.meal.consume.list

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
data class MealFact  @ParcelConstructor constructor(
    val fact: String?,
    val id: Int?
)