package com.villageapp.ui.dailyalerts.list

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.villageapp.R
import com.villageapp.base.BaseFragment
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.models.dailyalerts.detail.DailyAlert
import com.villageapp.network.RestResponse
import com.villageapp.ui.dailyalerts.ViewModelDailyAlerts
import com.villageapp.ui.dailyalerts.detail.ActivityAlertDetailPage
import com.villageapp.ui.dailyalerts.list.adapter.AdapterDailyAlertList
import com.villageapp.ui.user.register.ActivityLogInSignUp
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.fragment_daily_alert.*
import org.koin.android.architecture.ext.viewModel

class FragmentListDailyAlerts : BaseFragment(), AdapterViewClickListener<DailyAlert> {

    val viewModel: ViewModelDailyAlerts by viewModel()
    private lateinit var adapterDailyAlerts: AdapterDailyAlertList
    private var positionForMarkReadData: Int? = null

    companion object {


        fun getInstance(): FragmentListDailyAlerts {
            val bundle = Bundle()

            val fragment = FragmentListDailyAlerts()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_daily_alert


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setToolTitle(AndroidUtils.getString(R.string.daily_alerts))


        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        rcy_daily_alert_list.layoutManager = manager

        adapterDailyAlerts = AdapterDailyAlertList(this)

        rcy_daily_alert_list.adapter = adapterDailyAlerts


        subscribeUi()
        loadData()


    }


    private fun loadData() {
        viewModel.getDailyAlertList()
    }

    private fun subscribeUi() {

        viewModel.eventsDailyAlertList.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDailyAlertList(it.data?.data?.daily_alerts)
            }
        })


        viewModel.eventDailyAlertFavourite.observe(this, Observer { it ->
            when (it?.status) {
                RestResponse.Status.LOADING -> showProgressDialog(null, AndroidUtils.getString(R.string.loading))

                RestResponse.Status.ERROR -> {

                    hideProgressDialog()
                    showSnackbar(it.getErrorMessage(), false)
                }

                RestResponse.Status.SUCCESS -> {
                    hideProgressDialog()
                    onFavOrUnFavouriteSuccess(true)

                }

                RestResponse.Status.LOGIN -> {
                    startActivityForResult(
                        activity?.let { it1 -> ActivityLogInSignUp.getIntent(it1) },
                        ActivityLogInSignUp.REQUEST_CODE_DAILY_ALERT_FAV
                    )
                }
            }
        })

        viewModel.eventDailyAlertFavouriteRemove.observe(this, Observer { it ->
            when (it?.status) {
                RestResponse.Status.LOADING -> showProgressDialog(null, AndroidUtils.getString(R.string.loading))

                RestResponse.Status.ERROR -> {

                    hideProgressDialog()
                    showSnackbar(it.getErrorMessage(), false)
                }

                RestResponse.Status.SUCCESS -> {
                    hideProgressDialog()
                    onFavOrUnFavouriteSuccess(false)

                }
            }
        })
    }

    private fun onFavOrUnFavouriteSuccess(isFav: Boolean) {


        positionForMarkReadData?.let { adapterDailyAlerts.notifyMarkReadData(it, isFav) }

    }

    private fun showDailyAlertList(data: List<DailyAlert>?) {

        if (data != null) {
            showContentView()
            adapterDailyAlerts.submitListData(data)
        } else {
            showEmptyView(null, null)
        }
    }


    override fun onClickAdapterView(objectAtPosition: DailyAlert, viewType: Int, position: Int) {

        when (viewType) {
            Config.AdapterClickViewTypes.CLICK_VIEW_DAILY_ALERT_MARK_FAV_OR_UNFAV -> {

                objectAtPosition.id?.let {

                    this.positionForMarkReadData = position

                    if (true == objectAtPosition.is_favorite) {

                        viewModel.removeFavouriteDailyAlert(it)
                    } else {
                        viewModel.favouriteDailyAlert(it)

                    }
                }

            }
            Config.AdapterClickViewTypes.CLICK_VIEW_DAILY_ALERT_WHOLE -> {

                activity?.let {
                    startActivityForResult(
                        ActivityAlertDetailPage.getIntent(
                            activity,
                            objectAtPosition.id,
                            objectAtPosition.title,
                            objectAtPosition.adapterPosition
                        ), ActivityAlertDetailPage.FROM_DETAIL_PAGE
                    )
                }

            }
        }

    }


    override fun retryButtonClick() {
        super.retryButtonClick()
        loadData()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {


            RESULT_OK -> {


                when (requestCode) {
                    ActivityAlertDetailPage.FROM_DETAIL_PAGE -> {

                        data?.let {
                            val alertId = it.getIntExtra(ActivityAlertDetailPage.KEY_DAILY_ALERT_ID, -1)
                            val adpPosition = it.getIntExtra(ActivityAlertDetailPage.KEY_DAILY_LIST_ADP_POSITION, -1)
                            val isFavorite = it.getBooleanExtra(ActivityAlertDetailPage.KEY_DETAIL_DAILY_IS_FAV, false)

                            if (alertId != -1 && adpPosition != -1) {

                                adapterDailyAlerts.notifyDailyAlert(alertId, adpPosition, isFavorite)
                            }

                        }


                    }
                    ActivityLogInSignUp.REQUEST_CODE_DAILY_ALERT_FAV -> {

                        retryButtonClick()

                    }
                }

            }
        }
    }
}