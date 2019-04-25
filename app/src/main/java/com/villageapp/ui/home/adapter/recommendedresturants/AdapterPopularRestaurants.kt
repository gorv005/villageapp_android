package com.villageapp.ui.home.adapter.recommendedresturants

import android.app.Activity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.home.Restaurant
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.adp_view_recommended_resturents.view.*

class AdapterPopularRestaurants(
    private val adapterViewClickListener: AdapterViewClickListener<Restaurant>?,
    val activity: Activity
) : ListAdapter<Restaurant, AdapterPopularRestaurants.ViewHolder>(
    AdapterPopulerResturantCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.adp_view_recommended_resturents, parent, false)

        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)


        return AdapterPopularRestaurants.ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener)
    }

    fun notifyMealConsumed(restaurant: Restaurant) {

        restaurant.adapterPosition?.let {

            val item = getItem(it)
            if (item?.id == restaurant.id) {
                item?.is_meal_consumed = true
                notifyItemChanged(it)
            }

        }

    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {

        private val imageCornerRadius: Float = AndroidUtils.getDimension(R.dimen.margin_dp_10).toFloat()

        fun bind(restaurant: Restaurant, adapterViewClick: AdapterViewClickListener<Restaurant>?) {

            restaurant.adapterPosition = adapterPosition
            itemView.tv_resturant_name?.text = restaurant.name
            itemView.tv_resturant_phone_no?.text = restaurant.phone
            itemView.tv_resturant_address?.text = restaurant.address

            ImageRequestManager.with(itemView.sv_resturant_image)
                .url(restaurant.image)
                .roundedCornerRadius(imageCornerRadius)
                .build()



            itemView.tv_earn_free_points?.setOnClickListener {

                adapterViewClick?.onClickAdapterView(restaurant, Config.AdapterClickViewTypes.CLICK_VIEW_EARN_FREE_POINT, adapterPosition)
            }

            itemView.setOnClickListener {

                adapterViewClick?.onClickAdapterView(restaurant, Config.AdapterClickViewTypes.CLICK_VIEW_WHOLE, adapterPosition)
            }
        }
    }





}