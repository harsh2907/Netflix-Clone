package com.example.netflix.data_source.remote.response

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException

object RetrofitInterceptors {

    class RetryInterceptor(private val retryAttempts: Int) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            for (i in 1..retryAttempts) {
                try {
                    return chain.proceed(chain.request())
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                }
            }
            throw RuntimeException("failed to compile the request")
        }
    }

    class CacheInterceptor(private val context: Context) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            val originalResponse = chain.proceed(chain.request())
            return if (isOnline(context = context)) {
                val maxAge = 60
                originalResponse.newBuilder()
                    .addHeader("Cache-control", "public, max-age = $maxAge")
                    .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28 // 4 weeks
                originalResponse.newBuilder()
                    .addHeader("Cache-control", "public, only-if-cache max-age = $maxStale")
                    .build()
            }
        }
    }

    fun isOnline(context:Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        } else {
            null
        }

        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

}