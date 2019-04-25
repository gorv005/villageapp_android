package com.villageapp.models.home.search

data class Result(
    val resource: Resource?,
    val type: String?
) {

    companion object {

        const val PRODUCT = "Product"
        const val RESTAURANT = "Restaurant"
        const val CATEGORY = "Category"

        const val VIEW_TYPE_PRODUCT = 10
        const val VIEW_TYPE_RESTAURANT = 11
        const val VIEW_TYPE_CATEGORY = 12
    }

}