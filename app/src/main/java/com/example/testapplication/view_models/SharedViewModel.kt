package com.example.testapplication.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.ActivityScope
import com.example.testapplication.models.GithubJob
import javax.inject.Inject
import kotlin.properties.Delegates.notNull

class SharedViewModel@Inject constructor() : ViewModel() {

    val lastItemClicked: MutableLiveData<GithubJob> by lazy {
        MutableLiveData<GithubJob>()
    }

}