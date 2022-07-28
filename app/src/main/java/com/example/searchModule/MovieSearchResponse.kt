package com.example.searchModule

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<MovieInfo>,
)
data class MovieInfo(
    val title: String,
    val pubDate: String,
    val userRating: Int
)