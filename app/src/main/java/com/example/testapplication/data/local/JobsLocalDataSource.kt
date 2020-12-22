package com.example.testapplication.data.local

import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.testapplication.models.GithubJob
import com.google.gson.Gson
import javax.inject.Inject


class JobsLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val jobsDatabase: JobsDatabase
) {

    @Inject lateinit var gson: Gson
    val sharedPreferenceFavoritesLiveData = SharedPreferenceStringLiveData(
        sharedPreferences,
        "favorites",
        null
    )

    //region ROOM DB

    fun loadAllJobs() : LiveData<List<GithubJob>> = jobsDatabase.jobsDao().loadJobs()

    fun addAllJobs(jobs: List<GithubJob>) = jobsDatabase.jobsDao().saveJobs(jobs)

    //endregion

    //region SHARED PREFERENCES

//    fun addFavoriteToSP(job: GithubJob) {
//        loadFavoritesFromSP().apply {
//            val favorites = gson.toJson(this)
//            sharedPreferenceFavoritesLiveData.setValueForPreferences("favorites", favorites)
//            sharedPreferences.edit()?.putString("favorites", favorites)?.apply()
//        }
//    }

    fun addOrRemoveFavoriteFromSP(add: Boolean, job: GithubJob) {
        loadFavoritesFromSP().apply {
            if(add) this.add(job)
            else this.remove(job)
            val favorites = gson.toJson(this)
            sharedPreferenceFavoritesLiveData.setValueForPreferences("favorites", favorites)
//            sharedPreferences.edit()?.putString("favorites", favorites)?.apply()
        }
    }

    fun loadFavoritesFromSP() : MutableList<GithubJob> {
//        val sharedFavorites = sharedPreferences.getString("favorites", null)
        sharedPreferenceFavoritesLiveData.value?.let { listAsString ->
            return gson.fromJson(listAsString, Array<GithubJob>::class.java).toMutableList()
        } ?: return mutableListOf()
    }

    //endregion

}