package com.villageapp.models.dailyalerts.detail

data class DailyAlert(
    val content: String?,
    val id: Int?,
    val images: List<String>?,
    var is_favorite: Boolean?,
    var is_read: Boolean?,
    val points: Int?,
    val title: String?,
    var adapterPosition: Int?,
    var isDailyAlert: Boolean = true
)