package com.example.searchModule

import retrofit2.Response

interface MovieSearchView {
    fun onMovieSearchSuccess(result: MovieSearchResponse)
    fun onMovieSearchFailure()
}