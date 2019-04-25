package com.villageapp.ui.user.password.reset

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.models.user.password.forgot.ResponseForgotPassword
import com.villageapp.network.RestResponse
import com.villageapp.ui.user.password.ViewModelPassword
import com.villageapp.ui.user.password.forgot.ActivityForgotPasswordLinkSent
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.UiUtils
import kotlinx.android.synthetic.main.base_common_layout_signup_module.*
import kotlinx.android.synthetic.main.content_reset_password.*
import kotlinx.android.synthetic.main.view_new_confirm_password.*
import org.koin.android.architecture.ext.viewModel

class ActivityResetPassword : BaseActivity() {


    val viewModel: ViewModelPassword by viewModel()

    private var viewContentResetPassword: View? = null

    override fun getLayoutId() = R.layout.base_common_layout_signup_module


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nullParent = null


        viewContentResetPassword = LayoutInflater.from(this).inflate(R.layout.content_reset_password, nullParent)
        fl_container_add_content.addView(viewContentResetPassword)


        val emailToSendLink = intent?.getStringExtra(ActivityForgotPasswordLinkSent.KEY_EMAIL_TO_SEND_LINK)

        tv_resend_code.setOnClickListener {
            UiUtils.hideSoftKeyboard(it)

            if (!TextUtils.isEmpty(emailToSendLink)) {
                viewModel.forgotPasswordSendEmail(emailToSendLink!!)
            } else {
                showSnackbar("Email id not found", false)

            }

        }
        tv_reset_pwd.setOnClickListener {

            UiUtils.hideSoftKeyboard(this)

            val strCode = edt_verification_code.text.toString()
            val newPassword = edt_new_pwd.text.toString()
            val cnfrmPassword = edt_cnfrm_pwd.text.toString()


            val validateCodeErrorError = AndroidUtils.validateName(strCode)
            val validateNewPwdError = AndroidUtils.validatePassword(newPassword)
            val validateCnfrmPasswordError = AndroidUtils.validatePassword(cnfrmPassword)

            if (
                TextUtils.isEmpty(validateCodeErrorError)
                && TextUtils.isEmpty(validateNewPwdError)
                && TextUtils.isEmpty(validateCnfrmPasswordError)
            ) {


                if (newPassword == cnfrmPassword) {
                    viewModel.resetPassword(
                        newPassword,
                        cnfrmPassword,
                        strCode
                    )
                } else {
                    UiUtils.showSnackBar(this, AndroidUtils.getString(R.string.error_new_cnfrm_pwd_mismatch), false)
                }


            } else {
                edt_verification_code.error = validateCodeErrorError
                edt_new_pwd.error = validateNewPwdError
                edt_cnfrm_pwd.error = validateCnfrmPasswordError
            }
        }


        subscribeUi()
    }


    private fun subscribeUi() {
        viewModel.eventForgotPassword.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDataForgotPassword(it.data)
            }
        })

        viewModel.eventResetPassword.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDataResetPassword()
            }
        })
    }

    private fun showDataResetPassword() {

        hideProgressDialog()
        startActivity(ActivityResetPasswordConfirmation.getIntent(this))
        finish()
    }

    private fun showDataForgotPassword(data: ResponseForgotPassword?) {
        hideProgressDialog()
        showSnackbar(AndroidUtils.getString(R.string.a_verification_code_has_been_sent_to_your_registered_email_address_single_line), true)

    }


    override fun showLoadingView() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    override fun <T> showErrorView(response: RestResponse<T>?, imageResource: Int) {

        hideProgressDialog()

        showSnackbar(response?.getErrorMessage(), false)

    }


    companion object {

        val KEY_EMAIL_TO_SEND_LINK = "KEY_EMAIL_TO_SEND_LINK"

        fun getIntent(email: String?, context: Context): Intent {
            val intent = Intent(context, ActivityResetPassword::class.java)
            intent.putExtra(KEY_EMAIL_TO_SEND_LINK, email)

            return intent

        }
    }
}