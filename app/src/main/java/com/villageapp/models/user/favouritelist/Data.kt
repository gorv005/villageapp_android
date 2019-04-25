package com.villageapp.models.user.favouritelist

import com.villageapp.models.dailyalerts.detail.DailyAlert
import com.villageapp.models.home.Product

data class Data(
    val daily_alerts: List<DailyAlert>?,
    val products: List<Product>?
)