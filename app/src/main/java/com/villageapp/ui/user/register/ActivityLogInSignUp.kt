package com.villageapp.ui.user.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.callbacks.SignUpFragmentInterface
import com.villageapp.models.signUp.ResponseSignUp
import com.villageapp.models.signin.ResponseSignIn
import com.villageapp.ui.home.HomeActivity
import com.villageapp.utils.AndroidUtils

class ActivityLogInSignUp : BaseActivity(), SignUpFragmentInterface {


    override fun getLayoutId() = R.layout.activity_login_sign_up

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSignInFragment()
    }


    override fun setSignUpFragment() {
        AndroidUtils.replaceFragment(
            supportFragmentManager, R.id.login_sign_up_fragment_container,
            FragmentLoginSignUp.getInstance(false)
        )

    }

    override fun setSignInFragment() {
        AndroidUtils.replaceFragment(
            supportFragmentManager, R.id.login_sign_up_fragment_container,
            FragmentLoginSignUp.getInstance(true)
        )
    }

    override fun onSignUpSuccessful(response: ResponseSignUp?) {

//        UiUtils.showToast(this, Gson().toJson(response))
        setResult(Activity.RESULT_OK)
        finish()


    }


    override fun onSignInSuccessful(response: ResponseSignIn?) {

//        UiUtils.showToast(this, Gson().toJson(response))
        setResult(Activity.RESULT_OK)
        finish()
    }


    override fun onSkipClick() {
//        startActivity(HomeActivity.getIntent(this))
        finish()
    }


    companion object {

        var REQUEST_CODE_SAVE_PRODUCT = 1
        var REQUEST_CODE_OPEN_PROFILE = 2
        var REQUEST_CODE_DAILY_ALERT_FAV = 3
        var REQUEST_CODE_DAILY_ALERT_MARK_READ = 4
        var REQUEST_CODE_RESTAURANT_EARN_POINTS = 5
        var REQUEST_CODE_OPEN_NOTIFICATION = 6

        fun getIntent(context: Context) = Intent(context, ActivityLogInSignUp::class.java)
    }
}