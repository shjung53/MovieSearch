package com.example.searchModule

import android.util.Log
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


    fun movieSearch(id:String, pw:String, searchingText: String){
        val retrofit = Retrofit.Builder().baseUrl("https://openapi.naver.com").addConverterFactory(
            GsonConverterFactory.create()).build()

        val movieSearchService = retrofit.create(MovieSearchInterface::class.java)

        movieSearchService.getMovieInfo(id, pw, searchingText).enqueue(object : Callback<MovieSearchResponse>{
            override fun onResponse(call: Call<MovieSearchResponse>, movieSearchResponse: Response<MovieSearchResponse>
            ) {
                Log.d("전송",id)
                Log.d("결과",movieSearchResponse.toString())
                val response = movieSearchResponse.body()!!
                when(response.code){
                    400 -> movieSearchView.onMovieSearchFailure(response.code, response.message)
                    500 -> movieSearchView.onMovieSearchFailure(response.code, response.message)
                    else -> movieSearchView.onMovieSearchSuccess(response.message,response.result)
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                movieSearchView.onMovieSearchFailure(400, "네트워크 에러")
            }

        })
    }

}