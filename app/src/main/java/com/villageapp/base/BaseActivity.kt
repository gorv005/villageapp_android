package com.villageapp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.villageapp.R
import com.villageapp.models.notification.Notification
import com.villageapp.network.RestResponse
import com.villageapp.ui.dailyalerts.detail.ActivityAlertDetailPage
import com.villageapp.ui.home.HomeViewModel
import com.villageapp.ui.productdetail.ActivityProductDetailPage
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import com.villageapp.utils.UiUtils
import com.vlonjatg.progressactivity.ProgressFrameLayout
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import org.koin.android.architecture.ext.viewModel
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

private const val TIME_INTERVAL_DOUBLE_TAP_EXIT = 2000 // # milliseconds, desired time passed between two back presses.

abstract class BaseActivity : AppCompatActivity() {


    val baseViewModel: HomeViewModel by viewModel()

    abstract fun getLayoutId(): Int

    private var mBackPressed: Long = 0


    var overrideOnCreate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!overrideOnCreate) {
            setContentView(getLayoutId())
        }

    }


    override fun startActivity(intent: Intent?) {
        if (intent != null) {
            super.startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    fun exitOnDoubleTap() {
        if (mBackPressed + TIME_INTERVAL_DOUBLE_TAP_EXIT > System.currentTimeMillis()) {
            UiUtils.hideToast()
            super.onBackPressed()
            return
        } else {
            UiUtils.showToast(this, AndroidUtils.getString(R.string.click_back_to_exit))
        }

        mBackPressed = System.currentTimeMillis()
    }

    fun showSnackbar(string: String?, positive: Boolean) {
        UiUtils.showSnackBar(this, string, positive)
    }

    fun showProgressDialog(title: String?, message: String?, cancelable: Boolean = false) {
        UiUtils.showProgressDialog(this, title, message, cancelable)
    }

    fun hideProgressDialog() {
        UiUtils.dismissProgressDialog()
    }

    open fun showLoadingView() {
        findViewById<ProgressFrameLayout>(R.id.progressFrameLayout)?.showLoading()
    }

    open fun showContentView() {
        findViewById<ProgressFrameLayout>(R.id.progressFrameLayout)?.showContent()
    }

    open fun <T> showErrorView(response: RestResponse<T>?, @DrawableRes imageResource: Int = R.drawable.t_error_view_icon_internet_error) {
        findViewById<ProgressFrameLayout>(R.id.progressFrameLayout)
            ?.showError(
                response?.getErrorDrawable() ?: imageResource,
                response?.getErrorTitle()
                    ?: AndroidUtils.getString(R.string.error_view_default_title),
                response?.getErrorMessage()
                    ?: AndroidUtils.getString(R.string.error_view_default_desc),
                AndroidUtils.getString(R.string.default_error_button_retry_text)
            ) { retryButtonClick() }
    }

    open fun showEmptyView(
        title: String?,
        message: String?, @DrawableRes imageResource: Int = R.drawable.t_empty_view_icon_default
    ) {
        findViewById<ProgressFrameLayout>(R.id.progressFrameLayout)
            ?.showEmpty(
                imageResource,
                title ?: AndroidUtils.getString(R.string.empty_view_default_title),
                message ?: AndroidUtils.getString(R.string.empty_view_default_desc)
            )
    }

    /*
    * override this method to handle retry button click functionality in fragment/activity.
    *
    */
    open fun retryButtonClick() {
        // empty
    }

    /*
     * Calligraphy for custom font.
     *
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    fun showToolLogo(show: Boolean, onClickListener: View.OnClickListener) {
        iv_tool_app_logo?.visibility = if (show) View.VISIBLE else View.GONE
        iv_tool_app_logo?.setOnClickListener(onClickListener)
    }

    fun showToolBackButton(show: Boolean, onClickListener: View.OnClickListener) {
        iv_tool_left_back_button?.visibility = if (show) View.VISIBLE else View.GONE
        iv_tool_left_back_button?.setOnClickListener(onClickListener)
    }

    fun showToolBackButtonAndFinishOnClick() {
        showToolBackButton(true, View.OnClickListener { finish() })
    }

    fun setToolTitle(title: String?) {
        tv_tool_title?.text = title
    }


    //Notification handling

    fun notificationNavigation(notification: Notification?, isFromCloud: Boolean) {

        notification?.let { notificationObj ->

            var notifiableId: Int? = null

            notifiableId = if (isFromCloud) {
                notificationObj.notifiable_id
            } else {
                notificationObj.notifiable?.id

            }

            notifiableId?.let { itNotifiableId ->


                    notificationObj.notifiable_type?.let { type ->

                        when (type) {
                            Config.NotifiableTypes.PRODUCT -> {

                                startActivity(ActivityProductDetailPage.getIntent(this, itNotifiableId, ""))
                            }
                            Config.NotifiableTypes.DAILY_ALERT -> {

                                startActivity(ActivityAlertDetailPage.getIntent(this, itNotifiableId, "",-1))

                            }
                            Config.NotifiableTypes.USER -> {
                            }


                    }
                }

            }


        }
    }

}