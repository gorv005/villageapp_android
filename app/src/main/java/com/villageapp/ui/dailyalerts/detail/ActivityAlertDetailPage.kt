package com.villageapp.ui.dailyalerts.detail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.base.ImagesPagerAdapter
import com.villageapp.models.dailyalerts.detail.DailyAlert
import com.villageapp.network.RestResponse
import com.villageapp.ui.dailyalerts.ViewModelDailyAlerts
import com.villageapp.ui.user.register.ActivityLogInSignUp
import com.villageapp.utils.AndroidUtils
import kotlinx.android.synthetic.main.activity_alert_detail_page.*
import kotlinx.android.synthetic.main.content_daily_alert_detail.*
import org.koin.android.architecture.ext.viewModel

class ActivityAlertDetailPage : BaseActivity(), View.OnClickListener {


    val viewModel: ViewModelDailyAlerts by viewModel()
    private var dailyAlert: DailyAlert? = null


    private var alertName: String? = null
    private var alertId: Int? = null
    private var adpPosition: Int? = null

    override fun getLayoutId() = R.layout.activity_alert_detail_page

    companion object {
        const val KEY_DAILY_ALERT_ID = "KEY_DAILY_ALERT_ID"
        const val KEY_DAILY_ALERT_NAME = "KEY_DAILY_ALERT_NAME"
        const val KEY_DAILY_LIST_ADP_POSITION = "KEY_DAILY_LIST_ADP_POSITION"
        const val KEY_DETAIL_DAILY_IS_FAV = "KEY_DETAIL_DAILY_IS_FAV"

        const val FROM_DETAIL_PAGE = 77
        fun getIntent(context: Context?, id: Int?, alertName: String?, adpPositionAt: Int?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, ActivityAlertDetailPage::class.java)
                .putExtra(KEY_DAILY_ALERT_ID, id ?: 0)
                .putExtra(KEY_DAILY_ALERT_NAME, alertName)
                .putExtra(KEY_DAILY_LIST_ADP_POSITION, adpPositionAt)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alertName = intent?.getStringExtra(ActivityAlertDetailPage.KEY_DAILY_ALERT_NAME)
        alertId = intent?.getIntExtra(ActivityAlertDetailPage.KEY_DAILY_ALERT_ID, -1)
        adpPosition = intent?.getIntExtra(ActivityAlertDetailPage.KEY_DAILY_LIST_ADP_POSITION, -1)

        tv_title.text = alertName


        showToolBackButton(true, View.OnClickListener { onBackPressed() })

        iv_save.setOnClickListener(this)
        iv_mark_read.setOnClickListener(this)
        iv_mark_read.visibility = View.GONE


        Handler().postDelayed({
            try {
                iv_mark_read.visibility = View.VISIBLE
            } catch (e: Exception) {
            }
        }, 30 * 1000)


        subscribeUi()

        loadData()
    }


    override fun onClick(v: View?) {


        dailyAlert?.let { it ->


            it.id?.let()
            { _ ->


                when (v?.id) {
                    R.id.iv_save -> {


                        if (true == it.is_favorite) {

                            viewModel.removeFavouriteDailyAlert(it.id)
                        } else {
                            viewModel.favouriteDailyAlert(it.id)

                        }


                    }
                    R.id.iv_mark_read -> {

                        if (true != it.is_read) {

                            viewModel.markReadDailyAlert(it.id)
                        }

                    }
                }


            }

        }


    }


    private fun subscribeUi() {


        viewModel.eventsDailyAlertDetail.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> it.data?.data?.daily_alert?.let { it1 -> showData(it1) }
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
                        ActivityLogInSignUp.getIntent(this),
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


        viewModel.eventDailyAlertMarkRead.observe(this, Observer { it ->
            when (it?.status) {
                RestResponse.Status.LOADING -> showProgressDialog(null, AndroidUtils.getString(R.string.loading))

                RestResponse.Status.ERROR -> {

                    hideProgressDialog()
                    showSnackbar(it.getErrorMessage(), false)
                }

                RestResponse.Status.SUCCESS -> {
                    hideProgressDialog()
                    onMarkedAsRead()

                }

                RestResponse.Status.LOGIN -> {
                    startActivityForResult(
                        ActivityLogInSignUp.getIntent(this),
                        ActivityLogInSignUp.REQUEST_CODE_DAILY_ALERT_MARK_READ
                    )
                }
            }
        })

    }


    private fun showData(dailyAlert: DailyAlert) {

        this.dailyAlert = dailyAlert;

        showContentView()

        tv_title.text = dailyAlert.title
        tv_content.text = dailyAlert.content

        iv_save.setImageResource(if (true == dailyAlert.is_favorite) R.drawable.ic_bookmarked_true else R.drawable.ic_bookmarked_false)
        iv_mark_read.setImageResource(if (true == dailyAlert.is_read) R.drawable.ic_mark_read_activated else R.drawable.ic_mark_read)


        if (true == dailyAlert.is_read) {

            iv_mark_read.visibility = View.VISIBLE


            setPointText(dailyAlert)
        }


        dailyAlert.images?.let {

            val prodImagesPagerAdapter = ImagesPagerAdapter(supportFragmentManager, it)
            viewPager_dailyAlertImages.adapter = prodImagesPagerAdapter

            pageIndicatorViewDailyAlert.setViewPager(viewPager_dailyAlertImages)

        }


    }

    private fun setPointText(dailyAlert: DailyAlert) {
        var pointString = "point"
        dailyAlert.points?.let {
            pointString = if (it > 1) {
                it.toString() + " " + "points"
            } else {
                it.toString() + " " + "point"
            }
        }


        tv_earn_free_points_info.text = pointString + " earned"
        tv_earn_free_points_info.setTextColor(AndroidUtils.getColor(R.color.grey_shade_6))
    }


    private fun onFavOrUnFavouriteSuccess(isFav: Boolean) {

        dailyAlert?.is_favorite = isFav

        iv_save.setImageResource(if (true == dailyAlert?.is_favorite) R.drawable.ic_bookmarked_true else R.drawable.ic_bookmarked_false)

    }

    private fun onMarkedAsRead() {

        dailyAlert?.is_read = true

        iv_mark_read.setImageResource(if (true == dailyAlert?.is_read) R.drawable.ic_mark_read_activated else R.drawable.ic_mark_read)

        dailyAlert?.let { setPointText(it) }

    }


    private fun loadData() {

        alertId?.let { viewModel.dailyAlertDetail(it) }

    }

    override fun retryButtonClick() {
        super.retryButtonClick()
        loadData()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {


            Activity.RESULT_OK -> {


                when (requestCode) {
                    ActivityLogInSignUp.REQUEST_CODE_DAILY_ALERT_MARK_READ -> {

                        retryButtonClick()
                    }
                    ActivityLogInSignUp.REQUEST_CODE_DAILY_ALERT_FAV -> {

                        retryButtonClick()

                    }
                }

            }
        }
    }


    override fun onBackPressed() {
        val bundle = Bundle()
        alertId?.let { bundle.putInt(KEY_DAILY_ALERT_ID, it) }
        adpPosition?.let { bundle.putInt(KEY_DAILY_LIST_ADP_POSITION, it) }
        dailyAlert?.is_favorite?.let { bundle.putBoolean(KEY_DETAIL_DAILY_IS_FAV, it) }

        val mIntent = Intent()
        mIntent.putExtras(bundle)
        setResult(Activity.RESULT_OK, mIntent)
        super.onBackPressed()
    }
}