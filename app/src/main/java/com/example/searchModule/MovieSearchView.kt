package com.example.searchModule

interface MovieSearchView {
    fun onMovieSearchSuccess(message: String, result: ArrayList<MovieInfo>)
    fun onMovieSearchFailure(code: Int, message: String)
}