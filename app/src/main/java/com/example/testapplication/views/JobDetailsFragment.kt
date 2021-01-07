package com.example.testapplication.views

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.testapplication.R
import com.example.testapplication.models.GithubJob
import com.example.testapplication.utilities.githubJobTimeFormatter
import com.example.testapplication.utilities.loadFromUrlToGlide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_job_details.view.*
import javax.inject.Inject

class JobDetailsFragment:BaseFragment(R.layout.fragment_job_details) {

    @Inject lateinit var gson: Gson

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
        githubJobsViewModel.currentJob.value = (this.arguments?.getSerializable("job") as? GithubJob)

        githubJobsViewModel.sharedPreferenceFavoritesLiveData.observe(viewLifecycleOwner, { data ->
            data?.let {
                gson.fromJson(it, Array<GithubJob>::class.java)?.let { favorites ->
                    val item = favorites.find { favorite -> favorite.id == githubJobsViewModel.currentJob.value?.id  }
                    view.job_fragment_favorite.isSelected = item != null
                }
            }
        })
        githubJobsViewModel.currentJob.observe(viewLifecycleOwner, { job ->
            job?.apply {
                view.company_logo.loadFromUrlToGlide(companyLogo)
                view.job_fragment_job_title.text = title
                view.job_fragment_company_name.text = company
                view.job_fragment_job_location.text = location
                view.job_fragment_created.text = createdAt?.githubJobTimeFormatter()
                view.job_fragment_description.text = description
                view.job_fragment_type.text = type
                view.job_fragment_url.text = companyUrl
                view.job_fragment_apply.text = howToApply
                view.job_fragment_favorite.setOnClickListener {
                    it.isSelected = !it.isSelected
                    githubJobsViewModel.addOrRemoveFromPreferences(it.isSelected, this)
                }
            }
        })
    }

}