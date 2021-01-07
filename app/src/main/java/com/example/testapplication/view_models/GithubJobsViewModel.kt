package com.example.testapplication.view_models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.GithubJobsRepository
import com.example.testapplication.models.GithubJob
import kotlinx.coroutines.launch
import javax.inject.Inject

//@ActivityScope
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

    val currentJob : MutableLiveData<GithubJob> = MutableLiveData()

    fun getJobsFromPrefs() : MutableList<GithubJob>{
        return githubJobsRepository.getFavoritesJobsFromSP()
    }

    fun addJobFromPrefs(job: GithubJob){
        return githubJobsRepository.addFavoriteJobsFromSP(job)
    }

    fun removeJobFromPrefs(job: GithubJob){
        return githubJobsRepository.removeFavoriteJobsFromSP(job)
    }

    fun addOrRemoveFromPreferences(add: Boolean, job: GithubJob) = githubJobsRepository.addOrRemoveFavoriteJobsFromSP(add, job)

    val sharedPreferenceFavoritesLiveData = githubJobsRepository.sharedPreferenceFavoritesLiveData

    fun saveDataToFavorites(job: GithubJob) {
        sharedPreferenceFavoritesLiveData.value
    }


}