package com.villageapp.models.notification

data class PayloadDeviceRegistration(
    val device_registration_id: String,
    val device_type : String = "android"
)