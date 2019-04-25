package com.villageapp.models.meal.search


data class Meal(
    val description: String?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val points: Int?,
    var isSelected: Boolean = false
)