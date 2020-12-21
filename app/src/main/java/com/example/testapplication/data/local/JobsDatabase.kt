package com.example.testapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapplication.models.GithubJob

@Database(entities = [GithubJob::class], version = 1)
abstract class JobsDatabase : RoomDatabase() {
    abstract fun jobsDao(): JobsDao
}