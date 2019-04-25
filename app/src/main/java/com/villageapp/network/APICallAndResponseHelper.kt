package com.villageapp.network

import com.villageapp.callbacks.APIResponseCallback
import com.villageapp.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Response



object APICallAndResponseHelper {

    fun <T> call(observable: Observable<Response<T>>, callback: APIResponseCallback<T>?, compositeDisposable: CompositeDisposable?): Disposable {
        val disposable = observable.subscribe(
                { response -> onAcceptResponse(response, callback) },
                { throwable -> acceptThrowable(throwable, callback) })

        compositeDisposable?.add(disposable)

        return disposable
    }

    private fun <T> acceptThrowable(throwable: Throwable, callback: APIResponseCallback<T>?) {
        LogUtils.e(throwable)

        callback?.onResponse(RestResponse(throwable))
    }

    private fun <T> onAcceptResponse(response: Response<T>?, callback: APIResponseCallback<T>?) {
        callback?.onResponse(RestResponse(response))
    }
}
