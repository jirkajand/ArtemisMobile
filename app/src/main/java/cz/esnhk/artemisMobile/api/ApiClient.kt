package cz.esnhk.artemisMobile.api

import android.util.Log
import cz.esnhk.artemisMobile.api.AuthInterceptor.HeaderLoggingInterceptor
import cz.esnhk.artemisMobile.repository.DataStoreManager
import cz.esnhk.artemisMobile.services.AuthService
import cz.esnhk.artemisMobile.services.EventService
import cz.esnhk.artemisMobile.services.SemesterService
import cz.esnhk.artemisMobile.services.StudentService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    internal const val BASE_URL = "https://demo.artemis.esnhk.cz/api/v1/"

    private val logging = HttpLoggingInterceptor { message ->
        Log.d("API_LOGGER", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Service for auth (no token needed)
    val authService: AuthService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AuthService::class.java)
    }

    // Create services that need authentication
    fun createStudentService(dataStoreManager: DataStoreManager): StudentService {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(dataStoreManager))
            .addInterceptor(HeaderLoggingInterceptor())
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(StudentService::class.java)
    }

    fun createEventService(dataStoreManager: DataStoreManager): EventService {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(dataStoreManager))
            .addInterceptor(HeaderLoggingInterceptor())
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(EventService::class.java)
    }

    fun createSemesterService(dataStoreManager: DataStoreManager): SemesterService {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(dataStoreManager))
            .addInterceptor(HeaderLoggingInterceptor())
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SemesterService::class.java)
    }

}