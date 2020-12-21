package com.example.testapplication.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.testapplication.BuildConfig
import com.example.testapplication.MyApplication
import com.example.testapplication.data.remote.JobsRetrofitService
import com.example.testapplication.utilities.GITHUB_BASE_URL
import com.example.testapplication.utilities.JOBS_SHARED_PREFS
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *  You can use the @Provides annotation in Dagger modules
 *  to tell Dagger how to provide classes
 *  that your project doesn't own
 *  (e.g. an instance of Retrofit).
 */

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideJobsRetrofitService() : JobsRetrofitService {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(loggingInterceptor)
            }
//                connectTimeout(10, TimeUnit.MINUTES)
//                readTimeout(10, TimeUnit.MINUTES)
//                writeTimeout(10, TimeUnit.MINUTES)
        }.build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(GITHUB_BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobsRetrofitService::class.java)
    }
}