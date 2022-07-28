package com.example.searchModule

interface MovieSearchView {
    fun onMovieSearchSuccess(result: Result)
    fun onMovieSearchFailure(code: Int)
}