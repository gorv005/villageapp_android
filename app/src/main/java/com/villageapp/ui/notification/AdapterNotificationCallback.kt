package com.villageapp.ui.notification

import android.support.v7.util.DiffUtil
import com.villageapp.models.dailyalerts.detail.DailyAlert
import com.villageapp.models.home.Category
import com.villageapp.models.home.Restaurant
import com.villageapp.models.notification.Notification

class AdapterNotificationCallback : DiffUtil.ItemCallback<Notification>() {

    override fun areItemsTheSame(oldItem: Notification, newItem: Notification) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }
}