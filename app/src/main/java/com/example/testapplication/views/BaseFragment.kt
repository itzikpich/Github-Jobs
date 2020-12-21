package com.example.testapplication.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.testapplication.JobsActivity
import com.example.testapplication.view_models.GithubJobsViewModel
import com.example.testapplication.view_models.SharedViewModel
import javax.inject.Inject

open class BaseFragment(layoutRes:Int): Fragment(layoutRes) {

    open val jobsActivity get() = activity as JobsActivity

    // Fields that need to be injected by the jobs graph
    @Inject
    lateinit var githubJobsViewModel: GithubJobsViewModel
    @Inject
    lateinit var sharedViewModel: SharedViewModel

}