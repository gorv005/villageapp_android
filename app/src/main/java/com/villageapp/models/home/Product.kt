package com.villageapp.models.home

import com.villageapp.models.product.detail.ProductDirection
import com.villageapp.models.product.detail.ProductIngredient

data class Product(
    val cooking_time: String?,
    val id: Int?,
    var image: String?,
    var is_favorite: Boolean?,
    val name: String?,
    var images: List<String>?,
    val created_at: String,
    val product_directions: List<ProductDirection>?,
    val product_ingredients: List<ProductIngredient>?,
    val summary: String?,
    val updated_at: String?,
    var isProduct: Boolean = true
) {


    fun getProductImage(): String? {
        if (image == null && images != null) {

            if (!images!!.isEmpty()) {
                image = images!![0]
            }
        }

        return image
    }
}