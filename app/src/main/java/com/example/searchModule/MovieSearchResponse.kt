package com.example.searchModule

data class MovieSearchResponse(
    var lastBuildDate: String,
    var total: Int,
    var start: Int,
    var display: Int,
    var items: ArrayList<MovieInfo>?
)
data class MovieInfo(
    var title: String,
    var link: String,
    var image: String,
    var subtitle: String,
    var pubDate: String,
    var director: String,
    var actor: String,
    var userRating: String
)