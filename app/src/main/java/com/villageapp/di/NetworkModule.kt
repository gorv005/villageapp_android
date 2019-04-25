package com.villageapp.di

import android.annotation.SuppressLint
import com.villageapp.BuildConfig
import com.villageapp.network.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val EXTENDED_TIMEOUT = "EXTENDED_TIMEOUT"
const val NORMAL_TIMEOUT = "NORMAL_TIMEOUT"

val networkModule = applicationContext {

    bean { AppRestService(get(), get()) }

    bean { provideAppServiceFast(get(NORMAL_TIMEOUT), get()) }

    bean(EXTENDED_TIMEOUT) { provideOkHttpClientExtendedTimeOut(get()) }

    bean(NORMAL_TIMEOUT) { provideOkHttpClient(get()) }

    bean { provideGson() }

    bean { AuthInterceptor() }
}

private fun configureRetrofit(url: String, okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
}

@SuppressLint("SimpleDateFormat")
fun provideGson(): Gson {
    val builder = GsonBuilder()
    builder.disableHtmlEscaping()

    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    format.timeZone = TimeZone.getTimeZone("UTC")
    builder.setDateFormat(format.toLocalizedPattern())

    return builder.create()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    val builder = OkHttpClient().newBuilder()
    builder.readTimeout(30, TimeUnit.SECONDS)
    builder.connectTimeout(30, TimeUnit.SECONDS)
    builder.writeTimeout(30, TimeUnit.SECONDS)
    builder.addNetworkInterceptor(authInterceptor)
    if (BuildConfig.DEBUG) {
        val logging = CustomLoggingInterceptor()
        logging.setLevel(CustomLoggingInterceptor.Level.BODY)
        builder.addInterceptor(logging)
    }
    return builder.build()
}

fun provideOkHttpClientExtendedTimeOut(authInterceptor: AuthInterceptor): OkHttpClient {
    val builder = OkHttpClient().newBuilder()
    builder.readTimeout(5, TimeUnit.MINUTES)
    builder.connectTimeout(5, TimeUnit.MINUTES)
    builder.writeTimeout(5, TimeUnit.MINUTES)
    builder.addNetworkInterceptor(authInterceptor)
    if (BuildConfig.DEBUG) {
        val logging = CustomLoggingInterceptor()
        logging.setLevel(CustomLoggingInterceptor.Level.BODY)
        builder.addInterceptor(logging)
    }
    return builder.build()
}


fun provideAppServiceFast(okHttpClient: OkHttpClient, gson: Gson): AppRestApiFast {
    val retrofit = configureRetrofit(BuildConfig.BASE_URL, okHttpClient, gson)
    return retrofit.create(AppRestApiFast::class.java)
}