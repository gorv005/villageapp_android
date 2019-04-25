package com.villageapp.ui.home

import android.app.Activity
import android.content.Intent
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.callbacks.BottomNavClickCallbacks
import com.villageapp.ui.user.register.ActivityLogInSignUp
import kotlinx.android.synthetic.main.app_custom_bottom_nav_bar.*

abstract class HomeActivityBottomNav : BaseActivity() {


    fun bottomNavClickHandling(bottomNavClickCallbacks: BottomNavClickCallbacks?) {

        rl_nav_home.setOnClickListener {

            setSelectionFor(iv_nav_home)
            bottomNavClickCallbacks?.navHomeClick()
        }

        rl_nav_bolt.setOnClickListener {

            setSelectionFor(iv_nav_alert)
            bottomNavClickCallbacks?.navAlertClick()

        }

        rl_nav_notification.setOnClickListener {

            if (baseViewModel.isUserLoggedIn()) {

                setSelectionFor(iv_nav_notification)
                bottomNavClickCallbacks?.navNotificationClick()
            } else {
                startActivityForResult(
                    ActivityLogInSignUp.getIntent(this),
                    ActivityLogInSignUp.REQUEST_CODE_OPEN_NOTIFICATION
                )
            }


        }

        rl_nav_search.setOnClickListener {

            setSelectionFor(iv_nav_search)
            bottomNavClickCallbacks?.navSearchClick()
        }

        rl_nav_profile.setOnClickListener {

            if (baseViewModel.isUserLoggedIn()) {

                setSelectionFor(iv_nav_profile)
                bottomNavClickCallbacks?.navProfileClick()
            } else {
                startActivityForResult(
                    ActivityLogInSignUp.getIntent(this),
                    ActivityLogInSignUp.REQUEST_CODE_OPEN_PROFILE
                )
            }

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (Activity.RESULT_OK == resultCode) {

            if (ActivityLogInSignUp.REQUEST_CODE_OPEN_PROFILE == requestCode) {
                rl_nav_profile?.callOnClick()
            } else if (ActivityLogInSignUp.REQUEST_CODE_OPEN_NOTIFICATION == requestCode) {
                rl_nav_notification?.callOnClick()
            }
        }
    }


    private fun setSelectionFor(view: View) {


        iv_nav_home.setImageResource(R.drawable.ic_bottom_nav_home)
        iv_nav_home.background = null

        iv_nav_alert.setImageResource(R.drawable.ic_bottom_nav_bolt)
        iv_nav_alert.background = null

        iv_nav_notification.setImageResource(R.drawable.ic_bottom_nav_notification)
        iv_nav_notification.background = null

        iv_nav_search.setImageResource(R.drawable.ic_bottom_nav_search)
        iv_nav_search.background = null

        iv_nav_profile.setImageResource(R.drawable.ic_bottom_nav_profile)
        iv_nav_profile.background = null


        if (iv_nav_home == view) {

            iv_nav_home.setImageResource(R.drawable.ic_bottom_nav_home_selected)
            iv_nav_home.background = resources.getDrawable(R.drawable.ic_bottom_nav_selected)
        } else
            if (iv_nav_alert == view) {

                iv_nav_alert.setImageResource(R.drawable.ic_bottom_nav_bolt_selected)
                iv_nav_alert.background = resources.getDrawable(R.drawable.ic_bottom_nav_selected)
            } else
                if (iv_nav_notification == view) {

                    iv_nav_notification.setImageResource(R.drawable.ic_bottom_nav_notification_selected)
                    iv_nav_notification.background = resources.getDrawable(R.drawable.ic_bottom_nav_selected)
                } else
                    if (iv_nav_search == view) {

                        iv_nav_search.setImageResource(R.drawable.ic_bottom_nav_search_selected)
                        iv_nav_search.background = resources.getDrawable(R.drawable.ic_bottom_nav_selected)
                    } else
                        if (iv_nav_profile == view) {

                            iv_nav_profile.setImageResource(R.drawable.ic_bottom_nav_profile_selected)
                            iv_nav_profile.background = resources.getDrawable(R.drawable.ic_bottom_nav_selected)
                        }

    }

}