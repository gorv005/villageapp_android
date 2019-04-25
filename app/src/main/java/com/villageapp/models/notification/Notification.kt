package com.villageapp.models.notification

data class Notification(
    val created_at: String?,
    val id: Int?,
    val message: String?,
    val notifiable: Notifiable?,
    val notifiable_type: String?,
    val notification_type: String?,

    val user_id: Int?,
    val notifiable_id: Int?


)