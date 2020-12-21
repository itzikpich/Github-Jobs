package com.example.testapplication.views

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.adapters.GenericAdapter
import com.example.testapplication.models.GithubJob
import com.example.testapplication.utilities.flipFragment
import com.example.testapplication.view_holders.MovieViewHolder
import com.example.testapplication.view_models.GithubJobsViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

class FavoritesFragment: BaseFragment(R.layout.fragment_main) {

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

    val genericAdapter = object : GenericAdapter<Any>(
        clickAction = {
            onItemClicked(it)
        }) {

        override fun getLayoutId(position: Int, obj: Any): Int = R.layout.item_github_job

        override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = MovieViewHolder(view)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.fragmentMainRecyclerView.adapter = genericAdapter

    }

    override fun onResume() {
        super.onResume()
        val list = githubJobsViewModel.getJobsFromPrefs()//jobsActivity.loadFavoritesFromSP()
        genericAdapter.clearList()
        genericAdapter.setList(list)
    }

    fun onItemClicked(item: Any){
        (item as? GithubJob)?.let { job ->
            jobsActivity.flipFragment(JobDetailsFragment().apply { this.arguments = bundleOf("job" to job) })
        }
    }
}