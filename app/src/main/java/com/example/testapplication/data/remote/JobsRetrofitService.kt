package com.example.testapplication.data.remote

import com.example.testapplication.models.GithubJob
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobsRetrofitService {

    @GET("positions.json")
    suspend fun getJobs(
        @Query("description") description: String,
        @Query("page") page: Int,
        @Query("markdown") markdown: Boolean = true
    ) : Response<List<GithubJob>>

}