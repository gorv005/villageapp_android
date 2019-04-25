package com.villageapp.models.product.detail

import com.villageapp.models.home.Product

data class Data(
    val product: Product?,
    val related_products: List<Product>?
)