package com.topnotch.developers.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by promasterguru on 17/10/2021.
 */
class GithubApiService {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val API_TIMEOUT = 30L
        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        private val okHttpClient = OkHttpClient().newBuilder().apply {
            this.addInterceptor(loggingInterceptor)
            this.connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            this.writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            this.readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
        }.build()

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        fun getApiService(): GithubApi = retrofit.create(GithubApi::class.java)
    }
}