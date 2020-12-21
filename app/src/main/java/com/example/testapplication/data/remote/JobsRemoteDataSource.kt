package com.example.testapplication.data.remote

import javax.inject.Inject

class JobsRemoteDataSource @Inject constructor(
    private val apiService: JobsRetrofitService
) {

    suspend fun getJobsFromNetwork() = apiService.getJobs("android", 0)

}