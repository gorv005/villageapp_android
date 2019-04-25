package com.villageapp.ui.productdetail.ingredients

import android.support.v7.util.DiffUtil
import com.villageapp.models.home.Category
import com.villageapp.models.product.detail.ProductDirection
import com.villageapp.models.product.detail.ProductIngredient

class AdapterProductDetailIngredientCallback : DiffUtil.ItemCallback<ProductIngredient>() {

    override fun areItemsTheSame(oldItem: ProductIngredient, newItem: ProductIngredient) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ProductIngredient, newItem: ProductIngredient): Boolean {
        return oldItem == newItem
    }
}