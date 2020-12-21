package com.example.testapplication.data.local

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.example.testapplication.data.remote.JobsRetrofitService
import com.example.testapplication.models.GithubJob
import com.google.gson.Gson
import javax.inject.Inject

class JobsLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val jobsDatabase: JobsDatabase
) {

    @Inject lateinit var gson: Gson

    fun loadAllJobs() : LiveData<List<GithubJob>> = jobsDatabase.jobsDao().loadJobs()

    fun addAllJobs(jobs: List<GithubJob>) = jobsDatabase.jobsDao().saveJobs(jobs)

    fun addFavoriteToSP(job: GithubJob) {
        loadFavoritesFromSP().apply {
            this.add(job)
            val favorites = gson.toJson(this)
            sharedPreferences.edit()?.putString("favorites", favorites)?.apply()
        }
    }

    fun removeFavoriteFromSP(job: GithubJob) {
        loadFavoritesFromSP().apply {
            this.remove(job)
            val favorites = gson.toJson(this)
            sharedPreferences.edit()?.putString("favorites", favorites)?.apply()
        }
    }

    fun loadFavoritesFromSP() : MutableList<GithubJob> {
        val sharedFavorites = sharedPreferences.getString("favorites",null)
        sharedFavorites?.let {
            return gson.fromJson(sharedFavorites, Array<GithubJob>::class.java).toMutableList()
        } ?: return mutableListOf<GithubJob>()
    }

}