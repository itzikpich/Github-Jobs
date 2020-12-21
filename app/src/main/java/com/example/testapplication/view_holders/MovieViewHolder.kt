package com.example.testapplication.view_holders

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.adapters.GenericAdapter
import com.example.testapplication.models.GithubJob
import com.example.testapplication.utilities.githubJobTimeFormatter
import com.example.testapplication.utilities.loadFromUrlToGlide
import kotlinx.android.synthetic.main.item_github_job.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), GenericAdapter.Binder<GithubJob> {

    private var title: TextView = itemView.job_title
    private var description: TextView = itemView.job_type
    private var location: TextView = itemView.job_location
    private var companyName: TextView = itemView.company_name
    private var time: TextView = itemView.job_time_posted
    private var imageView: ImageView = itemView.company_image

    override fun bind(data: GithubJob) {
        title.text = data.title
        description.text = data.type
        location.text = data.location
        companyName.text = data.company
        time.text = data.createdAt?.githubJobTimeFormatter()
        imageView.loadFromUrlToGlide(data.companyLogo)
    }

}