package com.villageapp.ui.splash

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import com.villageapp.base.BaseRepository
import com.villageapp.base.BaseViewModel

const val SPLASH_NEXT_HOME_ACTIVITY = 1
const val SPLASH_NEXT_ON_BOARDING_ACTIVITY = 3

class SplashViewModel(repository: BaseRepository) : BaseViewModel<BaseRepository>(repository) {

    val nextIntent = MutableLiveData<Int>()

    fun loadData() {
        Handler().postDelayed({
            if (!isOnBoardingDone()) {
                setOnBoardingSeen(true)
                nextIntent.postValue(SPLASH_NEXT_ON_BOARDING_ACTIVITY)
            } else {
                nextIntent.postValue(SPLASH_NEXT_HOME_ACTIVITY)
            }
        }, 1500)
    }


}