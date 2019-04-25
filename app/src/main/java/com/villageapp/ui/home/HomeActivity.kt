package com.villageapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.gson.Gson
import com.villageapp.R
import com.villageapp.callbacks.BottomNavClickCallbacks
import com.villageapp.models.notification.Notification
import com.villageapp.ui.dailyalerts.list.FragmentListDailyAlerts
import com.villageapp.ui.notification.FragmentListNotification
import com.villageapp.ui.notification.ViewModelNotification
import com.villageapp.ui.search.FragmentHomeSearch
import com.villageapp.ui.user.profile.FragmentUserProfile
import com.villageapp.utils.AndroidUtils
import kotlinx.android.synthetic.main.app_custom_bottom_nav_bar.*
import org.koin.android.architecture.ext.viewModel

class HomeActivity : HomeActivityBottomNav(), BottomNavClickCallbacks {

    val viewModelNotification: ViewModelNotification by viewModel()

    var mFragHome: Fragment? = null
    var mFragProfile: Fragment? = null
    var mFragDailyAlerts: Fragment? = null
    var mFragNotificationList: Fragment? = null
    var mFragSearch: Fragment? = null

    override fun getLayoutId() = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavClickHandling(this)
        gotToHome()

        parseNotificationData(intent)
    }

    private fun parseNotificationData(intent: Intent?) {

        val extras = intent?.extras

        extras?.let { notificationData ->

            val dataStringString: String? = notificationData.get("data")?.toString()

            dataStringString?.let { it ->

                val notification: Notification = Gson().fromJson(it, Notification::class.java)
                notificationNavigation(notification, true)


            }

        }

    }

    fun gotToHome() {
        rl_nav_home.callOnClick()
    }

    private fun setHomeListFragment() {

//        if (mFragHome == null)
        mFragHome = HomeFragment.getInstance()

        AndroidUtils.replaceFragment(
            supportFragmentManager,
            R.id.homeFragmentContainer,
            mFragHome!!
        )

    }


    private fun setUserProfileFragment() {

//        if (mFragProfile == null)
        mFragProfile = FragmentUserProfile.getInstance()

        AndroidUtils.replaceFragment(
            supportFragmentManager,
            R.id.homeFragmentContainer,
            mFragProfile!!
        )

    }


    private fun setDailyAlertFragment() {

//        if (mFragDailyAlerts == null)
        mFragDailyAlerts = FragmentListDailyAlerts.getInstance()

        AndroidUtils.replaceFragment(
            supportFragmentManager,
            R.id.homeFragmentContainer,
            mFragDailyAlerts!!
        )

    }


    companion object {
        fun getIntent(context: Context) = Intent(context, HomeActivity::class.java)
        fun getIntent(context: Context, notificationExtras: Bundle?): Intent? {
            val intent = Intent(context, HomeActivity::class.java)

            if (notificationExtras != null) {
                intent.putExtras(notificationExtras)
            }

            return intent
        }
    }


    /*Bottom Nav Click Handling*/

    override fun navHomeClick() {
        setHomeListFragment()
    }

    override fun navAlertClick() {

        setDailyAlertFragment()

    }


    override fun navNotificationClick() {

        mFragNotificationList = FragmentListNotification.getInstance()

        AndroidUtils.replaceFragment(
            supportFragmentManager,
            R.id.homeFragmentContainer,
            mFragNotificationList!!
        )
    }

    override fun navSearchClick() {


        mFragSearch = FragmentHomeSearch.getInstance()

        AndroidUtils.replaceFragment(
            supportFragmentManager,
            R.id.homeFragmentContainer,
            mFragSearch!!
        )
    }

    override fun navProfileClick() {
        setUserProfileFragment()
    }


    override fun onBackPressed() {
        val currentFragment = supportFragmentManager?.findFragmentById(R.id.homeFragmentContainer)
        if (currentFragment is FragmentUserProfile) {

            if (currentFragment.canCloseScreen()) {
                super.onBackPressed()
            }

        } else {
            super.onBackPressed()

        }

    }


    override fun onResume() {
        super.onResume()

        viewModelNotification.registerDeviceForNotification();
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        parseNotificationData(intent)
    }
}