package com.villageapp.base

import android.arch.lifecycle.ViewModel
import com.villageapp.utils.LogUtils

open class BaseViewModel<T : BaseRepository?>(var repository: T) : ViewModel() {

    override fun onCleared() {
        LogUtils.d(null, "on cleared called and repository is $repository")

        repository?.onCleared()
        super.onCleared()
    }

    fun isUserLoggedIn() = repository?.isUserLoggedIn() == true
    fun isUserFBLoggedIn() = repository?.isUserFBLoggedIn() == true
    fun isOnBoardingDone() = repository?.isOnBoardingSeen() == true

     fun setOnBoardingSeen(isOnBoardingSeen: Boolean) {

         repository?.setPrefOnBoardingSeen(isOnBoardingSeen)
    }
}