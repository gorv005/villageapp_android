package com.villageapp.ui.user.password.reset

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseActivity
import kotlinx.android.synthetic.main.base_common_layout_signup_module.*
import kotlinx.android.synthetic.main.content_reset_password_confirmation.*

class ActivityResetPasswordConfirmation : BaseActivity() {

    private var viewContentResetPasswordConfirmation: View? = null

    override fun getLayoutId() = R.layout.base_common_layout_signup_module


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nullParent = null
        viewContentResetPasswordConfirmation =
                LayoutInflater.from(this).inflate(R.layout.content_reset_password_confirmation, nullParent)
        fl_container_add_content.addView(viewContentResetPasswordConfirmation)

        tv_back_to_login.setOnClickListener { finish() }
    }


    companion object {
        fun getIntent(context: Context) = Intent(context, ActivityResetPasswordConfirmation::class.java)
    }
}