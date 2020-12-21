package com.example.testapplication.view_models


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.ActivityScope
import com.example.testapplication.data.GithubJobsRepository
import com.example.testapplication.models.GithubJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class GithubJobsViewModel @Inject constructor(
    private val githubJobsRepository: GithubJobsRepository
) : ViewModel() {

    var githubJobsModelLiveData = githubJobsRepository.getAllJobsFromLocal()
        private set

    init {
        getJobs()
    }

    private fun getJobs(){
        viewModelScope.launch {
            val jobs = githubJobsRepository.getAllJobsAsync()
            githubJobsRepository.addAllJobsFromLocal(jobs)
        }
    }

    fun getJobsFromPrefs() : MutableList<GithubJob>{
        return githubJobsRepository.getFavoritesJobsFromSP()
    }

    fun addJobFromPrefs(job: GithubJob){
        return githubJobsRepository.addFavoriteJobsFromSP(job)
    }

    fun removeJobFromPrefs(job: GithubJob){
        return githubJobsRepository.removeFavoriteJobsFromSP(job)
    }



}