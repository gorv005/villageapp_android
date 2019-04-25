package com.villageapp.ui.home.adapter.recommendedproducts

import android.app.Activity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.home.Product
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.adp_view_recommended_products.view.*

class AdapterProducts(
    private val clickListener: (Product, Int) -> Unit,
    val activity: Activity,
    val isHorizontal: Boolean
) : ListAdapter<Product, AdapterProducts.ViewHolder>(
    AdapterPopulerProductsCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(
            if (isHorizontal) R.layout.adp_view_recommended_products_horizontal
            else R.layout.adp_view_recommended_products_vertical,
            parent,
            false
        )


        if (isHorizontal) {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels

            itemView.layoutParams = RecyclerView.LayoutParams(
                width - (width / 5),
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

        }

        return AdapterProducts.ViewHolder(itemView, activity, isHorizontal)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity, val isHorizontal: Boolean) :
        RecyclerView.ViewHolder(itemView) {

        private val imageCornerRadius: Float = AndroidUtils.getDimension(R.dimen.margin_dp_10).toFloat()

        fun bind(product: Product,clickListener: (Product, Int) -> Unit) {
            itemView.tv_resturant_name?.text = product.name
            itemView.tv_cooking_time?.text =product.cooking_time+ " "+AndroidUtils.getString(R.string.cooking_time)



            if (isHorizontal) {
                itemView.sv_product_image_horizontal.visibility = View.VISIBLE
                itemView.sv_product_image_vertical.visibility = View.GONE
            } else {
                itemView.sv_product_image_horizontal.visibility = View.GONE
                itemView.sv_product_image_vertical.visibility = View.VISIBLE
            }


            itemView.iv_book_mark.setImageResource(R.drawable.ic_bookmarked_false)

            product.is_favorite?.let{
                if(it)
                {
                    itemView.iv_book_mark.setImageResource(R.drawable.ic_bookmarked_true)
                }
            }


            ImageRequestManager.with(if (isHorizontal) itemView.sv_product_image_horizontal else itemView.sv_product_image_vertical)
                .url(product.getProductImage())
                .roundedCornerRadius(imageCornerRadius)
                .build()




            itemView.iv_book_mark?.setOnClickListener {
                clickListener(product, Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_SAVE)
            }

            itemView.setOnClickListener {
                clickListener(product, Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_DETAIL)
            }
        }
    }


}