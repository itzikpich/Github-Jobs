package com.example.testapplication.views

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.adapters.GenericAdapter
import com.example.testapplication.models.GithubJob
import com.example.testapplication.utilities.flipFragment
import com.example.testapplication.view_holders.MovieViewHolder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

class FavoritesFragment: BaseFragment(R.layout.fragment_main) {

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

        val genericAdapter = object : GenericAdapter<Any>(
            clickAction = {
                onItemClicked(view, it)
            }) {

            override fun getLayoutId(position: Int, obj: Any): Int = R.layout.item_github_job

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = MovieViewHolder(view)

        }

        setViewModel(genericAdapter)

        view.fragmentMainRecyclerView.adapter = genericAdapter

    }

    private fun setViewModel(genericAdapter: GenericAdapter<Any>) =
        githubJobsViewModel.sharedPreferenceFavoritesLiveData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                gson.fromJson(it, Array<GithubJob>::class.java)?.let { list ->
                    genericAdapter.clearList()
                    genericAdapter.setList(list.toMutableList())
                }
            }
        })

    fun onItemClicked(view: View, item: Any){
        (item as? GithubJob)?.let { job ->
//            sharedViewModel.lastItemClicked.value = job
//            view.findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(job))
            view.findNavController().navigate(R.id.action_to_detailsFrgment, bundleOf("job" to job))
//            jobsActivity.replaceFragment(JobDetailsFragment().apply { this.arguments = bundleOf("job" to job) })
        }
    }

}