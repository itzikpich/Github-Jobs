package com.example.testapplication.views

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.testapplication.R
import com.example.testapplication.models.GithubJob
import com.example.testapplication.utilities.githubJobTimeFormatter
import com.example.testapplication.utilities.loadFromUrlToGlide
import com.example.testapplication.view_models.GithubJobsViewModel
import kotlinx.android.synthetic.main.fragment_job_details.view.*
import javax.inject.Inject

class JobDetailsFragment:BaseFragment(R.layout.fragment_job_details) {

    var githubJob:GithubJob ?= null

    // Fields that need to be injected by the jobs graph
    @Inject
    lateinit var githubJobsViewModel: GithubJobsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
//      inject Dagger in the fragments's onAttach() method, no matter before or after super
        jobsActivity.jobsSubcomponent.inject(this)
//      Now you can access githubJobsViewModel here
//      and onCreateView too
//      (shared instance with the Activity and the other Fragments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        githubJob = (this.arguments?.getSerializable("job") as? GithubJob)
        githubJob?.apply {
            view.company_logo.loadFromUrlToGlide(companyLogo)
            view.job_fragment_job_title.text = title
            view.job_fragment_company_name.text = company
            view.job_fragment_job_location.text = location
            view.job_fragment_created.text = createdAt?.githubJobTimeFormatter()
            view.job_fragment_description.text = description
            view.job_fragment_type.text = type
            view.job_fragment_url.text = companyUrl
            view.job_fragment_apply.text = howToApply
            view.job_fragment_favorite.isSelected = this.isItemInFavorites()
            view.job_fragment_favorite.setOnClickListener {
                it.isSelected = !it.isSelected
            }
        }
    }

    override fun onPause() {
        super.onPause()
        githubJob?.let { githubJob ->
            if (view?.job_fragment_favorite?.isSelected == true && !githubJob.isItemInFavorites()) {
                githubJobsViewModel.addJobFromPrefs(githubJob)
            }
            if (view?.job_fragment_favorite?.isSelected == false && githubJob.isItemInFavorites()) {
                githubJobsViewModel.removeJobFromPrefs(githubJob)
            }
        }
    }

    private fun GithubJob.isItemInFavorites(): Boolean {
        val favorites = githubJobsViewModel.getJobsFromPrefs()
        val item = favorites.find { it.id == this.id  }
        return item != null
    }

}