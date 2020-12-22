package com.example.testapplication.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.JobsActivity
import com.example.testapplication.view_models.GithubJobsViewModel
import com.example.testapplication.view_models.SharedViewModel
import javax.inject.Inject

open class BaseFragment(layoutRes:Int): Fragment(layoutRes) {

    open val jobsActivity get() = activity as JobsActivity

    protected val githubJobsViewModel: GithubJobsViewModel by viewModels ({requireActivity()}) { viewModelFactory }
    // Fields that need to be injected by the jobs graph
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var sharedViewModel: SharedViewModel

}