package com.villageapp.ui.productdetail.directions

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.product.detail.ProductDirection
import com.villageapp.utils.AndroidUtils
import kotlinx.android.synthetic.main.adp_view_directions.view.*
import kotlinx.android.synthetic.main.line.view.*


class AdapterProductDetailDirections() : ListAdapter<ProductDirection, AdapterProductDetailDirections.ViewHolder>(
    AdapterProductDetailDirectionsCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.adp_view_directions, parent, false)


        return ViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),position,itemCount)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageCornerRadius: Float = AndroidUtils.getDimension(R.dimen.margin_dp_10).toFloat()

        fun bind(
            direction: ProductDirection,
            position: Int,
            itemCount: Int
        ) {
            itemView.tv_step_count?.text = "Step "+direction.order
            itemView.tv_step_desc?.text = direction.content

            ImageRequestManager.with(itemView.sv_image)
                .url(direction.image)
                .roundedCornerRadius(imageCornerRadius)
                .build()


            itemView.ll_top_indicator.visibility = View.VISIBLE
            itemView.ll_bottom_indicator.visibility = View.VISIBLE
            itemView.default_line.visibility = View.VISIBLE

            if(position == 0) {
                itemView.ll_top_indicator.visibility = View.INVISIBLE
                itemView.ll_bottom_indicator.visibility = View.VISIBLE

            }

            if(position == itemCount-1)
            {
                itemView.ll_top_indicator.visibility = View.VISIBLE
                itemView.ll_bottom_indicator.visibility = View.INVISIBLE
                itemView.default_line.visibility = View.INVISIBLE

            }

        }


    }


}