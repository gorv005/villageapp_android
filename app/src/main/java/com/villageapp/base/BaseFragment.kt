package com.villageapp.base

import android.content.Intent
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.network.RestResponse
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.UiUtils
import com.villageapp.utils.fragmentToolbar.FragmentToolbar
import com.villageapp.utils.fragmentToolbar.ToolbarManager
import com.vlonjatg.progressactivity.ProgressFrameLayout
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)

        return view
    }

    override fun startActivity(intent: Intent?) {
        if (intent != null) {
            super.startActivity(intent)
        } else {
            showSnackbar(AndroidUtils.getString(R.string.internal_error), false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // used for fragment toolbar implementation
        ToolbarManager(toolbarBuilder(), view).prepareToolbar()
    }

    abstract fun getLayoutId(): Int


    fun showSnackbar(string: String?, positive: Boolean) {
        UiUtils.showSnackBar(activity, string, positive)
    }

    fun showProgressDialog(title: String?, message: String?, cancelable: Boolean = false) {
        UiUtils.showProgressDialog(context, title, message, cancelable)
    }

    fun hideProgressDialog() {
        UiUtils.dismissProgressDialog()
    }

    open fun toolbarBuilder() = FragmentToolbar.Builder().build()

    open fun showLoadingView() {
        view?.findViewById<ProgressFrameLayout>(R.id.progressFrameLayout)?.showLoading()
    }

    open fun showContentView() {
        view?.findViewById<ProgressFrameLayout>(R.id.progressFrameLayout)?.showContent()
    }

    open fun <T> showErrorView(response: RestResponse<T>?, @DrawableRes imageResource: Int = R.drawable.t_error_view_icon_server_error) {
        view?.findViewById<ProgressFrameLayout>(R.id.progressFrameLayout)
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
        view?.findViewById<ProgressFrameLayout>(R.id.progressFrameLayout)
                ?.showEmpty(
                        imageResource,
                        title ?: AndroidUtils.getString(R.string.empty_view_default_title),
                        message ?: AndroidUtils.getString(R.string.empty_view_default_desc))
    }

    /*
    * override this method to handle retry button click functionality in fragment/activity.
    *
    * */
    open fun retryButtonClick() {
        // empty
    }

    /*
    * use this method to handle back press functionality from activity to fragment
    *
    * return true, if back press has been consumed and activity do not need to perform any additional handling.
    * return false, if fragment is not consuming the event and instead, activity should handle the back press event.
    * */
    open fun onBackPressed(): Boolean {
        return false
    }

    fun showToolLogo(show: Boolean, onClickListener: View.OnClickListener?) {
        iv_tool_app_logo?.visibility = if (show) View.VISIBLE else View.GONE
        iv_tool_app_logo?.setOnClickListener(onClickListener)
    }

    fun showToolBackButton(show: Boolean, onClickListener: View.OnClickListener) {
        iv_tool_left_back_button?.visibility = if (show) View.VISIBLE else View.GONE
        iv_tool_left_back_button?.setOnClickListener(onClickListener)
    }

    fun showToolBackButtonAndFinishOnClick() {
        showToolBackButton(true, View.OnClickListener { activity?.finish() })
    }

    fun setToolTitle(title: String?) {
        tv_tool_title?.text = title
    }
}