package com.villageapp.managers

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.villageapp.utils.LogUtils

class ActivityLifecycleManager : Application.ActivityLifecycleCallbacks {

    var currentActivity: String? = null
    var activityCount: Int = 0
    var resumedActivityCount: Int = 0

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activityCount++
    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {
        resumedActivityCount++

        currentActivity = null

        if (activity != null) {
            currentActivity = activity::class.java.name

            LogUtils.d("ActivityLifecycleManager",
                    "current = $currentActivity & resumed count = $resumedActivityCount & total count = $activityCount")
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        resumedActivityCount--
    }

    override fun onActivityStopped(activity: Activity?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {
        activityCount--
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        // nothing
    }
}