package com.villageapp.ui.dailyalerts.list.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.dailyalerts.detail.DailyAlert
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.adp_view_daily_alert.view.*

class AdapterDailyAlertList(private val adapterViewClickListener: AdapterViewClickListener<DailyAlert>?) :
    ListAdapter<DailyAlert, AdapterDailyAlertList.ViewHolder>(AdapterDailyAlertCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.adp_view_daily_alert, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener)
    }

    private var dataList: List<DailyAlert>? = null

    fun submitListData(data: List<DailyAlert>) {
        this.dataList = data
        submitList(data)
    }

    fun notifyMarkReadData(positionForMarkReadData: Int, isFav: Boolean) {

        try {
            dataList?.get(positionForMarkReadData)?.is_favorite = isFav
            notifyItemChanged(positionForMarkReadData)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun notifyDailyAlert(alertId: Int, adpPosition: Int, favorite: Boolean) {

        try {

            val item = getItem(adpPosition)
            if (alertId == item?.id) {
                item.is_favorite = favorite
                notifyItemChanged(adpPosition)
            }
        } catch (e: Exception) {
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageCornerRadius: Float = AndroidUtils.getDimension(R.dimen.margin_dp_10).toFloat()

        fun bind(dailyAlert: DailyAlert, adapterViewClick: AdapterViewClickListener<DailyAlert>?) {

            dailyAlert.adapterPosition = adapterPosition


            itemView.iv_daily_alert_book_mark?.setImageResource(
                if (true == dailyAlert.is_favorite)
                    R.drawable.ic_bookmarked_true
                else
                    R.drawable.ic_bookmarked_false
            )


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

                adapterViewClick?.onClickAdapterView(
                    dailyAlert,
                    Config.AdapterClickViewTypes.CLICK_VIEW_DAILY_ALERT_MARK_FAV_OR_UNFAV, adapterPosition
                )
            }

            itemView.setOnClickListener {

                adapterViewClick?.onClickAdapterView(
                    dailyAlert,
                    Config.AdapterClickViewTypes.CLICK_VIEW_DAILY_ALERT_WHOLE, adapterPosition
                )
            }
        }
    }




}