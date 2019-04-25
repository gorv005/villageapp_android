package com.villageapp.ui.saveditems

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.user.favouritelist.UserSavedFavourites
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.adp_view_daily_alert.view.*
import kotlinx.android.synthetic.main.adp_view_recommended_products.view.*


class AdapterUserSavedListing(
    private val adapterViewClickListener: AdapterViewClickListener<UserSavedFavourites>?
) : ListAdapter<UserSavedFavourites, AdapterUserSavedListing.ViewHolder>(
    AdapterSavedProductCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(
            if (viewType == UserSavedFavourites.PRODUCT)
                R.layout.adp_view_recommended_products_vertical
            else
                R.layout.adp_view_daily_alert,
            parent,
            false
        )


        return AdapterUserSavedListing.ViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {

        val dataAtPos = getItem(position)

        dataAtPos?.type?.let { return it }

        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageCornerRadius: Float = AndroidUtils.getDimension(R.dimen.margin_dp_10).toFloat()

        fun bind(
            userSavedFavourite: UserSavedFavourites,
            adapterViewClickListener: AdapterViewClickListener<UserSavedFavourites>?
        ) {


            when (userSavedFavourite.type) {

                UserSavedFavourites.DAILY_ALERTS -> {

                    bindDailyAlert(userSavedFavourite, adapterViewClickListener)

                }

                UserSavedFavourites.PRODUCT -> {

                    bindProduct(userSavedFavourite, adapterViewClickListener)


                }

            }

        }


        private fun bindProduct(
            userSavedFavourite: UserSavedFavourites,
            adapterViewClickListener: AdapterViewClickListener<UserSavedFavourites>?
        ) {


            userSavedFavourite.products?.let { product ->
                itemView.tv_resturant_name?.text = product.name
                itemView.tv_cooking_time?.text = product.cooking_time + " " +
                        AndroidUtils.getString(R.string.cooking_time)

                itemView.sv_product_image_vertical.visibility = View.VISIBLE
                itemView.sv_product_image_horizontal.visibility = View.GONE

                itemView.iv_book_mark.setImageResource(R.drawable.ic_bookmarked_true)



                ImageRequestManager.with(itemView.sv_product_image_vertical)
                    .url(product.getProductImage())
                    .roundedCornerRadius(imageCornerRadius)
                    .build()




                itemView.iv_book_mark?.setOnClickListener {
                    adapterViewClickListener?.onClickAdapterView(
                        userSavedFavourite,
                        Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_SAVE,
                        adapterPosition
                    )
                }

                itemView.setOnClickListener {
                    adapterViewClickListener?.onClickAdapterView(
                        userSavedFavourite,
                        Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_DETAIL,
                        adapterPosition
                    )
                }
            }


        }

        private fun bindDailyAlert(
            userSavedFavourite: UserSavedFavourites,
            adapterViewClickListener: AdapterViewClickListener<UserSavedFavourites>?
        ) {


            userSavedFavourite.dailyAlerts?.let { dailyAlert ->

                dailyAlert.adapterPosition = adapterPosition


                itemView.iv_daily_alert_book_mark?.setImageResource(R.drawable.ic_bookmarked_true)


                if (!dailyAlert.images.isNullOrEmpty()) {
                    ImageRequestManager.with(itemView.sv_daily_alert)
                        .url(dailyAlert.images[0])
                        .roundedCornerRadius(imageCornerRadius)
                        .build()
                } else {
                    ImageRequestManager.with(itemView.sv_daily_alert)
                        .url(null)
                        .roundedCornerRadius(imageCornerRadius)
                        .build()
                }


                itemView.tv_daily_alert_name?.text = dailyAlert.title


                itemView.iv_daily_alert_book_mark?.setOnClickListener {

                    adapterViewClickListener?.onClickAdapterView(
                        userSavedFavourite,
                        Config.AdapterClickViewTypes.CLICK_VIEW_DAILY_ALERT_MARK_FAV_OR_UNFAV, adapterPosition
                    )
                }

                itemView.setOnClickListener {

                    adapterViewClickListener?.onClickAdapterView(
                        userSavedFavourite,
                        Config.AdapterClickViewTypes.CLICK_VIEW_DAILY_ALERT_WHOLE, adapterPosition
                    )
                }
            }


        }


    }


}