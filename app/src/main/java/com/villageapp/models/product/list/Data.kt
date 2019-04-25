package com.villageapp.models.product.list

import com.villageapp.models.home.Category
import com.villageapp.models.home.Product

data class Data(
    val category: Category?,
    val products: List<Product>?,
    val page: Int?,
    val per_page: Int?
)