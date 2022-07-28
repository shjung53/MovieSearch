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


    fun movieSearch(clientId:String, clientSecret:String, searchingText: ByteString){
        val retrofit = Retrofit.Builder().baseUrl("https://openapi.naver.com").addConverterFactory(
            GsonConverterFactory.create()).build()

        val movieSearchService = retrofit.create(MovieSearchInterface::class.java)

        movieSearchService.getMovieInfo(clientId, clientSecret, searchingText).enqueue(object : Callback<MovieSearchResponse>{
            override fun onResponse(call: Call<MovieSearchResponse>, movieSearchResponse: Response<MovieSearchResponse>
            ) {
                Log.d("결과",movieSearchResponse.toString())
                val response = movieSearchResponse.body()!!
                when(response.code){
                    200 -> movieSearchView.onMovieSearchSuccess(response.result)
                    else -> movieSearchView.onMovieSearchFailure(response.code)
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                movieSearchView.onMovieSearchFailure(400)
            }

        })
    }

}