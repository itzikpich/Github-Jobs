package com.example.testapplication.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.testapplication.data.local.JobsDatabase
import com.example.testapplication.utilities.JOBS_SHARED_PREFS
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideGson() : Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideSharedPrefs(application: Application) : SharedPreferences {
        return application.getSharedPreferences(JOBS_SHARED_PREFS, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, JobsDatabase::class.java, "jobs-db").build()
}