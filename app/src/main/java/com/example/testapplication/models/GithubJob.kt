package com.example.testapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class GithubJob(
    @PrimaryKey
    val id: String,
    val company: String?,
    val description: String?,
    val location: String?,
    val title: String?,
    val type: String?,
    val url: String?,
    @SerializedName("company_logo")
    val companyLogo: String?,
    @SerializedName("company_url")
    val companyUrl: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("how_to_apply")
    val howToApply: String?
) : Serializable {

}