package com.villageapp.ui.home.adapter.recommendedproducts

import android.support.v7.util.DiffUtil
import com.villageapp.models.home.Category
import com.villageapp.models.home.Product

class AdapterPopulerProductsCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}