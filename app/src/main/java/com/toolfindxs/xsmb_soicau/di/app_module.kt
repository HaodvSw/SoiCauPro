package com.toolfindxs.xsmb_soicau.di


import com.toolfindxs.xsmb_soicau.data.remote.config.TokenInterceptor
import com.google.gson.GsonBuilder
import com.toolfindxs.xsmb_soicau.BuildConfig
import com.toolfindxs.xsmb_soicau.data.local.Preferences
import com.toolfindxs.xsmb_soicau.data.local.prefs.AppPrefsHelper
import com.toolfindxs.xsmb_soicau.data.local.prefs.PrefsHelper
import com.toolfindxs.xsmb_soicau.data.remote.IApiRepository
import com.toolfindxs.xsmb_soicau.data.remote.config.NetworkInterceptor
import com.toolfindxs.xsmb_soicau.utils.Const
import com.utils.SchedulerProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val app_module: Module = module {
    single { SchedulerProvider() }

    single { AppPrefsHelper(get(), "SoiCau") as PrefsHelper }

//    single { AppDbHelper(get()) as DbHelper }

    single { GsonBuilder().create()!! }
    single { Preferences.getInstance(get()) }
    single<IApiRepository> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApiRepository::class.java)
    }

    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(TokenInterceptor())
            .addInterceptor(NetworkInterceptor(get()))
            .connectTimeout(Const.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Const.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}

val AppModule = listOf(
    app_module,
    view_module
)