package com.villageapp.ui.home.adapter.popularrecipies

import android.support.v7.util.DiffUtil
import com.villageapp.models.home.Category

class AdapterPopulerRecipesCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}