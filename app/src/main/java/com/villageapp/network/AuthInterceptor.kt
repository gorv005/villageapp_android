package com.villageapp.network


import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        //        builder.addHeader("deviceType", Config.FIXParams.DEVICE_TYPE);
        //        builder.addHeader("apiVersion", Config.FIXParams.API_VERSION);
//        builder.addHeader("Content-Type", "application/json")
        return chain.proceed(builder.build())
    }
}
