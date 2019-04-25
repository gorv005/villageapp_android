package com.villageapp.ui.dailyalerts.list.adapter

import android.support.v7.util.DiffUtil
import com.villageapp.models.dailyalerts.detail.DailyAlert
import com.villageapp.models.home.Category
import com.villageapp.models.home.Restaurant

class AdapterDailyAlertCallback : DiffUtil.ItemCallback<DailyAlert>() {

    override fun areItemsTheSame(oldItem: DailyAlert, newItem: DailyAlert) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: DailyAlert, newItem: DailyAlert): Boolean {
        return oldItem == newItem
    }
}