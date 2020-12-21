package com.example.testapplication.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.data.local.JobsLocalDataSource
import com.example.testapplication.data.remote.JobsRemoteDataSource
import com.example.testapplication.models.GithubJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GithubJobsRepository remains in ApplicationComponent
 * because it's scoped to the ApplicationComponent.
 * If the project grows, you want to share the same instance across different features.
 *
 * Because GithubJobsRepository is part of ApplicationComponent,
 * its dependencies (i.e. LocalDataSource and RemoteDataSource) need to be in this component too
 * in order to be able to provide instances of Repository
 * */

@Singleton
class GithubJobsRepository @Inject constructor(
    private val localDataSource: JobsLocalDataSource,
    private val remoteDataSource: JobsRemoteDataSource
) {

    fun getAllJobsFromLocal() : LiveData<List<GithubJob>> {
        return localDataSource.loadAllJobs()
    }

    suspend fun addAllJobsFromLocal(jobs: List<GithubJob>) {
        withContext(Dispatchers.IO) {
            localDataSource.addAllJobs(jobs)
        }
    }

    suspend fun getAllJobsAsync() : List<GithubJob> {
        var data = emptyList<GithubJob>()
        try {
            val response = remoteDataSource.getJobsFromNetwork()

            //todo - error handling

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    data = body
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    fun getFavoritesJobsFromSP() = localDataSource.loadFavoritesFromSP()
    fun addFavoriteJobsFromSP(githubJob: GithubJob) = localDataSource.addFavoriteToSP(githubJob)
    fun removeFavoriteJobsFromSP(githubJob: GithubJob) = localDataSource.removeFavoriteFromSP(githubJob)

}