package com.villageapp.models.user.favouritelist

import com.villageapp.models.dailyalerts.detail.DailyAlert
import com.villageapp.models.home.Product


data class UserSavedFavourites(
    var dailyAlerts: DailyAlert?,
    var products: Product?,
    var type: Int?


) {
    fun areSame(new: UserSavedFavourites): Boolean {
        try {
            if (type == new.type) {
                if (DAILY_ALERTS == type) {
                    return dailyAlerts?.id == new.dailyAlerts?.id
                } else if (PRODUCT == type) {
                    return products?.id == new.products?.id
                }
            }
        } catch (e: Exception) {
        }

        return false
    }

    companion object {

        const val DAILY_ALERTS = 88
        const val PRODUCT = 89
    }
}


