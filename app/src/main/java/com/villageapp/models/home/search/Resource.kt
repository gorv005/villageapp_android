package com.villageapp.models.home.search

data class Resource(
    val address: String?,
    val cooking_time: String?,
    val created_at: String?,
    val id: Int?,
    var image: String?,
    val images: List<String>?,
    val is_meal_consumed: Boolean?,
    val name: String?,
    val phone: String?,
    val points: Int?,
    val product_count: Int?,
    val updated_at: String?
){

    fun getProductImage(): String? {
        if (image == null && images != null) {

            if (!images!!.isEmpty()) {
                image = images!![0]
            }
        }

        return image
    }
}