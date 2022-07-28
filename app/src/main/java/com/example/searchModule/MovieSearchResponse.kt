package com.example.searchModule

import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieSearchResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result,
)

data class Result(
    val lastBuildDate: Date,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: ArrayList<MovieInfo>
)
data class MovieInfo(
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: Int
)