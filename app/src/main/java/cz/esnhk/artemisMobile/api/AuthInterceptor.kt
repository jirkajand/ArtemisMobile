package cz.esnhk.artemisMobile.api

import android.util.Log
import cz.esnhk.artemisMobile.repository.DataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val dataStoreManager: DataStoreManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // BLOCKING fetch token because Interceptor is synchronous
        val token = runBlocking {
            dataStoreManager.getToken()
        }

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }

    class HeaderLoggingInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            Log.d("HEADER_LOGGER", "--> ${request.method} ${request.url}")
            for ((name, value) in request.headers) {
                Log.d("HEADER_LOGGER", "$name: $value")
            }
            Log.d("HEADER_LOGGER", "--> END ${request.method}")

            return chain.proceed(request)
        }
    }

}