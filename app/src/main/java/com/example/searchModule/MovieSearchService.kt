package com.example.searchModule

import android.util.Log
import okio.ByteString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieSearchService {
    private lateinit var movieSearchView: MovieSearchView

    fun setMovieSearchView(movieSearchView: MovieSearchView){
        this.movieSearchView = movieSearchView
    }


    fun movieSearch(clientId:String, clientSecret:String, searchingText: String){
        val retrofit = Retrofit.Builder().baseUrl("https://openapi.naver.com/v1/").addConverterFactory(
            GsonConverterFactory.create()).build()

        val movieSearchService = retrofit.create(MovieSearchInterface::class.java)

        movieSearchService.getMovieInfo(clientId, clientSecret, searchingText).enqueue(object : Callback<MovieSearchResponse>{
            override fun onResponse(call: Call<MovieSearchResponse>, movieSearchResponse: Response<MovieSearchResponse>) {
                val response = movieSearchResponse.body()!!
                Log.d("결과",response.toString())
                when(movieSearchResponse.code()){
                    200 -> movieSearchView.onMovieSearchSuccess(response)
                    else -> movieSearchView.onMovieSearchFailure()
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                movieSearchView.onMovieSearchFailure()
            }
        })
    }

}