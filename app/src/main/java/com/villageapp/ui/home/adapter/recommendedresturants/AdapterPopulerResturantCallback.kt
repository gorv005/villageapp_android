package com.villageapp.ui.home.adapter.recommendedresturants

import android.support.v7.util.DiffUtil
import com.villageapp.models.home.Category
import com.villageapp.models.home.Restaurant

class AdapterPopulerResturantCallback : DiffUtil.ItemCallback<Restaurant>() {

    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }
}