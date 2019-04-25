package com.villageapp.ui.notification

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.notification.Notification
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.adp_view_notification.view.*

class AdapterNotificationList(private val adapterViewClickListener: AdapterViewClickListener<Notification>?) :
    ListAdapter<Notification, AdapterNotificationList.ViewHolder>(AdapterNotificationCallback()) {

    private var dataList: List<Notification>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.adp_view_notification, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener)
    }


    fun submitListData(data: List<Notification>) {
        this.dataList = data
        submitList(data)
    }

//    fun notifyMarkReadData(positionForMarkReadData: Int, isFav: Boolean) {
//
//        try {
//            dataList?.get(positionForMarkReadData)?.is_favorite = isFav
//            notifyItemChanged(positionForMarkReadData)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    fun notifyDailyAlert(alertId: Int, adpPosition: Int, favorite: Boolean) {
//
//        try {
//
//            val item = getItem(adpPosition)
//            if (alertId == item?.id) {
//                item.is_favorite = favorite
//                notifyItemChanged(adpPosition)
//            }
//        } catch (e: Exception) {
//        }
//
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(notification: Notification, adapterViewClick: AdapterViewClickListener<Notification>?) {


            if (!notification.notifiable?.images.isNullOrEmpty()) {
                ImageRequestManager.with(itemView.sv_notification_image)
                    .url(notification.notifiable?.images?.get(0))
                    .circular(true)
                    .build()
            } else {

                ImageRequestManager.with(itemView.sv_notification_image)
                    .url(null)
                    .circular(true)
                    .build()
            }



            itemView.tv_notification_text?.text = notification.message

            itemView.setOnClickListener {

                adapterViewClick?.onClickAdapterView(
                    notification,
                    Config.AdapterClickViewTypes.CLICK_VIEW_NOTIFICATION, adapterPosition
                )
            }
        }
    }


}