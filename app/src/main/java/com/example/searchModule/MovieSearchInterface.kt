package com.example.searchModule

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieSearchInterface {
    @GET("/v1/search/movie.json")
    fun getMovieInfo(
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") pw: String,
        @Query("query") searchingText: String
    ): Call<MovieSearchResponse>
}