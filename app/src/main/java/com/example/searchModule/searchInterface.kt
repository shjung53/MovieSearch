package com.example.searchModule

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class searchInterface {
    @GET("/v1/search/movie.json")
    fun getMovieInfo(
        @Header()
        @Query()
    )
}