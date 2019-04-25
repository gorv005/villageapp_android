package com.villageapp.ui.productdetail.directions

import android.support.v7.util.DiffUtil
import com.villageapp.models.home.Category
import com.villageapp.models.product.detail.ProductDirection

class AdapterProductDetailDirectionsCallback : DiffUtil.ItemCallback<ProductDirection>() {

    override fun areItemsTheSame(oldItem: ProductDirection, newItem: ProductDirection) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ProductDirection, newItem: ProductDirection): Boolean {
        return oldItem == newItem
    }
}