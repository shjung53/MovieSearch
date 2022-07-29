package com.example.searchModule

interface MovieSearchView {
    fun onMovieSearchSuccess(result: MovieSearchResponse)
    fun onMovieSearchFailure()
}