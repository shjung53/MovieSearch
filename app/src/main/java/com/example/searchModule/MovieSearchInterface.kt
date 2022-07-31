package com.example.searchModule
import okio.ByteString
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieSearchInterface {
    @GET("search/movie.json")
    fun getMovieInfo(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") searchingText: String,
        @Query("start") start: Int
    ): Call<MovieSearchResponse>
}