package com.villageapp.ui.user.password.forgot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.ui.user.password.reset.ActivityResetPassword
import kotlinx.android.synthetic.main.base_common_layout_signup_module.*
import kotlinx.android.synthetic.main.content_forgot_password_link_confirmation.*

class ActivityForgotPasswordLinkSent : BaseActivity() {

    private var viewContentForgotPasswordLinkSent: View? = null

    override fun getLayoutId() = R.layout.base_common_layout_signup_module


    private var emailToSendLink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nullParent = null

        viewContentForgotPasswordLinkSent =
                LayoutInflater.from(this).inflate(R.layout.content_forgot_password_link_confirmation, nullParent)
        fl_container_add_content.addView(viewContentForgotPasswordLinkSent)


        emailToSendLink = intent?.getStringExtra(KEY_EMAIL_TO_SEND_LINK)

        tv_go_to_reset_pwd.setOnClickListener {

            startActivity(ActivityResetPassword.getIntent(emailToSendLink, this))
            finish()
        }

    }


    companion object {

        val KEY_EMAIL_TO_SEND_LINK = "KEY_EMAIL_TO_SEND_LINK"

        fun getIntent(email: String, context: Context): Intent {
            val intent = Intent(context, ActivityForgotPasswordLinkSent::class.java)
            intent.putExtra(KEY_EMAIL_TO_SEND_LINK, email)

            return intent

        }
    }
}