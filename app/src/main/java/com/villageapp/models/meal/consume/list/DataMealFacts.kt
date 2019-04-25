package com.villageapp.models.meal.consume.list

import com.villageapp.models.user.info.get.User
import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
data class DataMealFacts @ParcelConstructor constructor(
    val user: User?,
    val meal_facts: List<MealFact>?
)