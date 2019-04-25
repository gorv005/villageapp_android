package com.villageapp.ui.search

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.home.search.Result
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.adp_view_popular_recipies.view.*
import kotlinx.android.synthetic.main.adp_view_recommended_products.view.*
import kotlinx.android.synthetic.main.adp_view_recommended_resturents.view.*
import kotlinx.android.synthetic.main.adp_view_recommended_resturents.view.tv_resturant_name


class AdapterHomeSearch(private val adapterViewClickListener: AdapterViewClickListener<Result>) :
    ListAdapter<Result, AdapterHomeSearch.ViewHolder>(AdapterHomeSearchCallback()) {


    override fun getItemViewType(position: Int): Int {

        val objAtPos = getItem(position)

        objAtPos?.type?.let {

            when (it) {
                Result.CATEGORY -> {

                    return Result.VIEW_TYPE_CATEGORY
                }
                Result.PRODUCT -> {
                    return Result.VIEW_TYPE_PRODUCT
                }
                Result.RESTAURANT -> {
                    return Result.VIEW_TYPE_RESTAURANT
                }
                else -> {
                    return super.getItemViewType(position)
                }
            }
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var layoutId = R.layout.adp_view_popular_recipies

        when (viewType) {
            Result.VIEW_TYPE_CATEGORY -> {
                layoutId = R.layout.adp_view_popular_recipies_vertical
            }
            Result.VIEW_TYPE_PRODUCT -> {

                layoutId = R.layout.adp_view_recommended_products_vertical
            }
            Result.VIEW_TYPE_RESTAURANT -> {

                layoutId = R.layout.adp_view_recommended_resturents_search

            }
        }

        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), getItemViewType(position), adapterViewClickListener)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageCornerRadius: Float = AndroidUtils.getDimension(R.dimen.margin_dp_10).toFloat()

        fun bind(result: Result, viewType: Int, adapterViewClickListener: AdapterViewClickListener<Result>) {

            when (viewType) {
                Result.VIEW_TYPE_CATEGORY -> {
                    bindCategory(result, adapterViewClickListener)
                }
                Result.VIEW_TYPE_PRODUCT -> {
                    bindProduct(result, adapterViewClickListener)
                }
                Result.VIEW_TYPE_RESTAURANT -> {
                    bindRestaurant(result, adapterViewClickListener)
                }
            }

        }

        private fun bindRestaurant(result: Result, adapterViewClickListener: AdapterViewClickListener<Result>) {


            result.resource?.let { restaurant ->

                itemView.tv_resturant_name?.text = restaurant.name
                itemView.tv_resturant_phone_no?.text = restaurant.phone
                itemView.tv_resturant_address?.text = restaurant.address

                ImageRequestManager.with(itemView.sv_resturant_image)
                    .url(restaurant.image)
                    .roundedCornerRadius(imageCornerRadius)
                    .build()



                itemView.tv_earn_free_points.visibility = View.GONE
//                itemView.tv_earn_free_points?.setOnClickListener {
//
//                    adapterViewClickListener.onClickAdapterView(
//                        result,
//                        Config.AdapterClickViewTypes.CLICK_VIEW_EARN_FREE_POINT, adapterPosition
//                    )
//                }

                itemView.setOnClickListener {

                    adapterViewClickListener.onClickAdapterView(
                        result,
                        Config.AdapterClickViewTypes.CLICK_VIEW_WHOLE, adapterPosition
                    )
                }

            }


        }

        private fun bindProduct(result: Result, adapterViewClickListener: AdapterViewClickListener<Result>) {


            result.resource?.let { product ->
                itemView.tv_resturant_name?.text = product.name
                itemView.tv_cooking_time?.text = product.cooking_time+" "+AndroidUtils.getString(R.string.cooking_time)

                itemView.sv_product_image_horizontal.visibility = View.GONE
                itemView.sv_product_image_vertical.visibility = View.VISIBLE

                itemView.iv_book_mark.setImageResource(R.drawable.ic_bookmarked_false)

                itemView.iv_book_mark.visibility = View.GONE

                ImageRequestManager.with(itemView.sv_product_image_vertical)
                    .url(product.getProductImage())
                    .roundedCornerRadius(imageCornerRadius)
                    .build()




                itemView.iv_book_mark?.setOnClickListener {

                    adapterViewClickListener.onClickAdapterView(
                        result,
                        Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_SAVE,
                        adapterPosition
                    )
                }

                itemView.setOnClickListener {
                    adapterViewClickListener.onClickAdapterView(
                        result,
                        Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_DETAIL,
                        adapterPosition
                    )
                }
            }


        }

        private fun bindCategory(result: Result, adapterViewClickListener: AdapterViewClickListener<Result>) {

            result.resource?.let { category ->
                itemView.tv_recipe_catgeory_name?.text = category.name
                itemView.tv_recipe_total_count?.text = category.product_count?.toString() + " Recipes"

                ImageRequestManager.with(itemView.sv_recipes_image)
                    .url(category.image)
                    .roundedCornerRadius(imageCornerRadius)
                    .build()

                itemView.setOnClickListener {

                    adapterViewClickListener.onClickAdapterView(
                        result,
                        Config.AdapterClickViewTypes.CLICK_VIEW_RECIPE_OR_CATAGEORY, adapterPosition
                    )

                }
            }


        }

    }


}