package com.example.testapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapplication.models.GithubJob

@Dao
interface JobsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveJobs(githubJob: List<GithubJob>)

    @Query("SELECT * FROM githubjob WHERE id = :id")
    fun loadJob(id: String): LiveData<GithubJob>

    @Query("SELECT * FROM githubjob")
    fun loadJobs(): LiveData<List<GithubJob>>

}