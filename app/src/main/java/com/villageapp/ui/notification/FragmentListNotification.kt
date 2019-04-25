package com.villageapp.ui.notification

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.base.BaseFragment
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.models.notification.Notification
import com.villageapp.network.RestResponse
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.fragment_daily_alert.*
import org.koin.android.architecture.ext.viewModel

class FragmentListNotification : BaseFragment(), AdapterViewClickListener<Notification> {

    val viewModel: ViewModelNotification by viewModel()
    private lateinit var adapterNotifications: AdapterNotificationList

    companion object {


        fun getInstance(): FragmentListNotification {
            val bundle = Bundle()

            val fragment = FragmentListNotification()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_daily_alert


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setToolTitle(AndroidUtils.getString(R.string.notifications))


        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        rcy_daily_alert_list.layoutManager = manager

        adapterNotifications = AdapterNotificationList(this)

        rcy_daily_alert_list.adapter = adapterNotifications


        subscribeUi()
        loadData()


    }


    private fun loadData() {
        viewModel.getNotificationList()
    }

    private fun subscribeUi() {

        viewModel.eventnotificationList.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDailyAlertList(it.data?.data?.notifications)
            }
        })

    }


    private fun showDailyAlertList(data: List<Notification>?) {

        if (!data.isNullOrEmpty()) {
            showContentView()
            adapterNotifications.submitListData(data)
        } else {
            showEmptyView("No notification for you", "")
        }
    }


    override fun onClickAdapterView(objectAtPosition: Notification, viewType: Int, position: Int) {

        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_NOTIFICATION -> {

                activity?.let {


                    if (activity is BaseActivity) {
                        (activity as BaseActivity).notificationNavigation(objectAtPosition, false)
                    }

                }

            }
        }

    }


    override fun retryButtonClick() {
        super.retryButtonClick()
        loadData()
    }


}