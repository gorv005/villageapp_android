package com.villageapp.network

import com.villageapp.R
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.isEmpty
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class RestResponse<T> {

    enum class Status {
        LOADING, SUCCESS, ERROR, LOGIN
    }

    var data: T? = null
    var status: Status? = null
    private var message: String? = null
    private var networkError: Boolean = false

    private var response: Response<T>? = null
    private var throwable: Throwable? = null

    constructor() {
        this.status = Status.LOADING
    }

    constructor(status : RestResponse.Status) {
        this.status = status
    }

    // in case of successful response from server
    constructor(response: Response<T>?) {
        this.response = response
        this.message = null
        this.networkError = false

        if (response?.isSuccessful == true) {
            data = response.body()
            status = Status.SUCCESS
        } else {
            data = response?.body()
            status = Status.ERROR

            parseErrorMessage(response)
        }
    }

    private fun parseErrorMessage(response: Response<T>?) {
        try {
            val jsonObject = JSONObject(response?.errorBody()?.string())
            message = jsonObject.getString("message")
        } catch (e: Exception) {
            // ignore
        }

        if (isEmpty(message)) {
            message = AndroidUtils.getString(R.string.server_error)
        }
    }

    // in case of throwable from observable
    constructor(throwable: Throwable?) {
        this.status = Status.ERROR
        this.throwable = throwable

        networkError = false
        message = AndroidUtils.getString(R.string.internal_error)

        if (throwable is UnknownHostException || throwable is IOException) {
            message = AndroidUtils.getString(R.string.no_network_message)
            networkError = true
        } else if (throwable is SocketTimeoutException) {
            message = AndroidUtils.getString(R.string.request_timed_out_message)
            networkError = true
        } else if (throwable is HttpException) {
            networkError = false
            message = AndroidUtils.getString(R.string.server_error)
        }
    }

    fun getErrorMessage(): String {
        return message ?: AndroidUtils.getString(R.string.request_timed_out_message)
    }

    fun getErrorTitle(): String {
        return "Error"
    }

    fun getErrorDrawable(): Int {
        if (networkError) {
            return R.drawable.t_error_view_icon_internet_error
        } else {
            return R.drawable.t_error_view_icon_server_error
        }
    }

    fun getResponseCode() = response?.code()
}