package com.kiran.fampaytask.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class APIClient {
    val apiInterface: APIInterface

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofitClient = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
        apiInterface = retrofitClient.create(APIInterface::class.java)
    }

    companion object {
        private const val BASE_URL: String = "https://run.mocky.io/v3/"
        private var apiClient: APIClient? = null
        val instance: APIClient
            get() {
                if (apiClient == null) apiClient = APIClient()
                return apiClient as APIClient
            }

    }
}