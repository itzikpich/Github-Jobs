package com.example.testapplication.data.network

import com.example.testapplication.models.GithubJob
import com.example.testapplication.utilities.GITHUB_BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("positions.json")
    suspend fun getJobs(
        @Query("markdown") markdown: Boolean = true,
        @Query("description") description: String = "android",
        @Query("page") page: Int = 0
    ) : Response<List<GithubJob>>

    companion object {
        // init Retrofit base server instance
        val githubClient by lazy { invoke(GITHUB_BASE_URL) }

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        operator fun invoke(baseUrl: String): ApiService {
            val client = OkHttpClient.Builder().apply {
                addNetworkInterceptor(loggingInterceptor)
//                connectTimeout(10, TimeUnit.MINUTES)
//                readTimeout(10, TimeUnit.MINUTES)
//                writeTimeout(10, TimeUnit.MINUTES)
            }.build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}