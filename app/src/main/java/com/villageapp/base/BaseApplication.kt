package com.villageapp.base

import android.os.Bundle
import android.support.multidex.MultiDexApplication
import com.villageapp.BuildConfig
import com.villageapp.R
import com.villageapp.di.appModule
import com.villageapp.di.networkModule
import com.villageapp.managers.ActivityLifecycleManager
import com.villageapp.utils.AndroidUtils
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.core.CrashlyticsCore
import com.facebook.drawee.backends.pipeline.Fresco
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


class BaseApplication : MultiDexApplication() {

    val activityLifecycleManager: ActivityLifecycleManager? by inject()

    var deepLinkBundle: Bundle? = null

    override fun onCreate() {
        super.onCreate()
        sInstance = this

        startKoin(this, listOf(appModule, networkModule))

        Fresco.initialize(this)

        // FABRIC
        // Initializes Fabric for builds that don't use the debug build type.
        val crashlyticsKit = Crashlytics.Builder()
                .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build()

        Fabric.with(this, crashlyticsKit, Answers())
        // END FABRIC

        registerActivityLifecycleCallbacks(activityLifecycleManager)

        // Calligraphy for custom font
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath(AndroidUtils.getString(R.string.font_path_circular_std_book))
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
        // end calligraphy
    }

    companion object {

        private lateinit var sInstance: BaseApplication

        fun getInstance() = sInstance
    }
}