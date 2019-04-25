package com.villageapp.ui.splash

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import com.facebook.common.util.UriUtil
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.base.BaseApplication
import com.villageapp.managers.ImageRequestManager
import com.villageapp.ui.home.HomeActivity
import com.villageapp.ui.onboarding.OnboardingActivity
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.UiUtils
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.architecture.ext.viewModel


class SplashActivity : BaseActivity() {

    val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        overrideOnCreate = true
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        UiUtils.loadImage(R.drawable.village_app_logo, logo_image)

        ImageRequestManager.with(splash_image)
            .uri(UriUtil.getUriForResourceId(R.drawable.z_splash_bg_image))
            .build()

        subscribeUi()

        viewModel.loadData()

        AndroidUtils.printHashKey(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent

        BaseApplication.getInstance().deepLinkBundle = intent?.extras
    }

    override fun getLayoutId() = R.layout.activity_splash

    private fun subscribeUi() {
        viewModel.nextIntent.observe(this, Observer {
            startActivity(
                when (it) {
                    SPLASH_NEXT_HOME_ACTIVITY -> {

                        var extras = intent?.extras
                        HomeActivity.getIntent(this, extras)
                    }
                    SPLASH_NEXT_ON_BOARDING_ACTIVITY -> OnboardingActivity.getIntent(this)
                    else -> OnboardingActivity.getIntent(this)
                }
            )

            finish()
        })
    }
}
