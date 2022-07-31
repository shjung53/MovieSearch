package com.example.movieSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchModule.MovieInfo

class SearchViewModel: ViewModel() {

    private val _movieList = MutableLiveData<ArrayList<MovieInfo>>()
    val movieList: LiveData<ArrayList<MovieInfo>> get() = _movieList

    fun updateMovieList(movieList: ArrayList<MovieInfo>){
        _movieList.value = movieList
    }

    private val _page = MutableLiveData<Int>()
    val page : LiveData<Int> get() = _page

    fun updatePage(page: Int){
        _page.value = page
    }

}