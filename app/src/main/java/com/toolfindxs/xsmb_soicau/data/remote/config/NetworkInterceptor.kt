package com.toolfindxs.xsmb_soicau.data.remote.config

import android.content.Context
import com.toolfindxs.xsmb_soicau.data.local.prefs.PrefsHelper
import com.toolfindxs.xsmb_soicau.utils.hasNetworkConnection
import com.toolfindxs.xsmb_soicau.R
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException

class NetworkInterceptor constructor(val context: Context) : Interceptor, KoinComponent {
    private val pref by inject<PrefsHelper>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = ""
        /*
         * We check if there is internet
         * available in the device. If not, pass
         * the networkState as NO_INTERNET.
         * */
        when {
            !hasNetworkConnection(context) -> {
                throw NoConnectivityException(context)
            }
            token != null && token.isNotEmpty() -> {
                return chain.proceed(
                    chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                )
            }
            else -> {
                return chain.proceed(
                    chain.request()
                        .newBuilder()
                        .build()
                )
            }
        }
    }


    class NoConnectivityException(val context: Context) : IOException() {
        override fun getLocalizedMessage(): String? {
            return context.resources.getString(R.string.no_internet_connection)
        }
    }
}