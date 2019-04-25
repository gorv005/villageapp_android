package com.villageapp.ui.user.password.change

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.network.RestResponse
import com.villageapp.ui.user.password.ViewModelPassword
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.UiUtils
import kotlinx.android.synthetic.main.content_change_password.*
import kotlinx.android.synthetic.main.content_change_password_confirmation.*
import kotlinx.android.synthetic.main.view_new_confirm_password.*
import org.koin.android.architecture.ext.viewModel

class ActivityChangePassword : BaseActivity() {


    val viewModel: ViewModelPassword by viewModel()

    override fun getLayoutId() = R.layout.activity_change_password


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setToolTitle(AndroidUtils.getString(R.string.change_password))
        showToolBackButtonAndFinishOnClick()

        ll_change_pwd_confirmation.visibility = View.GONE
        ns_change_pwd.visibility = View.VISIBLE


        tv_back_to_login.setOnClickListener {
            finish()
        }

        tv_change_pwd.setOnClickListener {

            UiUtils.hideSoftKeyboard(this)

            val strCurrentPwd = edt_current_pwd.text.toString()
            val newPassword = edt_new_pwd.text.toString()
            val cnfrmPassword = edt_cnfrm_pwd.text.toString()


            val validateCurrentPwdError = AndroidUtils.validatePassword(strCurrentPwd)
            val validateNewPwdError = AndroidUtils.validatePassword(newPassword)
            val validateCnfrmPasswordError = AndroidUtils.validatePassword(cnfrmPassword)

            if (
                TextUtils.isEmpty(validateCurrentPwdError)
                && TextUtils.isEmpty(validateNewPwdError)
                && TextUtils.isEmpty(validateCnfrmPasswordError)
            ) {


                if (newPassword == cnfrmPassword) {
                    viewModel.changePassword(
                        strCurrentPwd,
                        newPassword,
                        cnfrmPassword
                    )
                } else {
                    UiUtils.showSnackBar(this, AndroidUtils.getString(R.string.error_new_cnfrm_pwd_mismatch), false)
                }


            } else {
                edt_current_pwd.error = validateCurrentPwdError
                edt_new_pwd.error = validateNewPwdError
                edt_cnfrm_pwd.error = validateCnfrmPasswordError
            }

        }

        subscribeUi()


    }


    private fun subscribeUi() {
        viewModel.eventChangePassword.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDataChangePassword()
            }
        })


    }

    private fun showDataChangePassword() {
        hideProgressDialog()

        ll_change_pwd_confirmation.visibility = View.VISIBLE
        ns_change_pwd.visibility = View.GONE

    }


    companion object {


        fun getIntent(context: Context): Intent {
            val intent = Intent(context, ActivityChangePassword::class.java)

            return intent

        }
    }





    override fun showLoadingView() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    override fun <T> showErrorView(response: RestResponse<T>?, imageResource: Int) {

        hideProgressDialog()

        showSnackbar(response?.getErrorMessage(), false)

    }
}