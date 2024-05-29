package com.toolfindxs.xsmb_soicau

import android.app.Application
import android.provider.Settings
import com.core.rx.RxBus
import com.toolfindxs.xsmb_soicau.di.AppModule
import org.joda.time.DateTime
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class MainApp : Application() {

    private lateinit var  rxBus: RxBus

    companion object {
        private lateinit var instance: MainApp

        @JvmStatic
        fun getInstance(): MainApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        rxBus = RxBus()
        startKoin {
            androidContext(this@MainApp)
            modules(AppModule)
            logger(EmptyLogger())
        }
    }

    val deviceId: String
        get() = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

    val eventBus: RxBus
        get() = rxBus

    var date: DateTime = DateTime()
    var dateSelect: DateTime
        set(value) {
            date = value
        }
        get() = date
}