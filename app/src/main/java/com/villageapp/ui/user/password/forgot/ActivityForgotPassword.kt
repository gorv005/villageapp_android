package com.villageapp.ui.user.password.forgot

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.network.RestResponse
import com.villageapp.ui.user.password.ViewModelPassword
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.UiUtils
import kotlinx.android.synthetic.main.base_common_layout_signup_module.*
import kotlinx.android.synthetic.main.content_forgot_password.*
import kotlinx.android.synthetic.main.view_email.*
import org.koin.android.architecture.ext.viewModel

class ActivityForgotPassword : BaseActivity() {


    val viewModel: ViewModelPassword by viewModel()


    private var viewContentforgotPassword: View? = null

    override fun getLayoutId() = R.layout.base_common_layout_signup_module


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nullParent = null

        viewContentforgotPassword = LayoutInflater.from(this).inflate(R.layout.content_forgot_password, nullParent)
        fl_container_add_content.addView(viewContentforgotPassword)

        tv_sent_verification_mail
            .setOnClickListener {

                UiUtils.hideSoftKeyboard(this)
                val validateEmailError = AndroidUtils.validateEmail(edt_email.text.toString())

                if (
                    TextUtils.isEmpty(validateEmailError)
                ) {

                    viewModel.forgotPasswordSendEmail(edt_email.text.toString())


                } else {
                    edt_email.error = validateEmailError
                }


            }

        tv_back_to_sign_in
            .setOnClickListener { finish() }


        subscribeUi()

    }

    private fun subscribeUi() {
        viewModel.eventForgotPassword.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showData()
            }
        })
    }


    override fun showLoadingView() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    override fun <T> showErrorView(response: RestResponse<T>?, imageResource: Int) {

        hideProgressDialog()

        showSnackbar(response?.getErrorMessage(), false)

    }

    private fun showData() {

        hideProgressDialog()
        startActivity(ActivityForgotPasswordLinkSent.getIntent(edt_email.text.toString(),this))
        finish()

    }


    companion object {
        fun getIntent(context: Context) = Intent(context, ActivityForgotPassword::class.java)
    }
}