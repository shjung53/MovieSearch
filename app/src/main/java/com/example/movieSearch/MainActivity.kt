package com.example.movieSearch
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieSearch.databinding.ActivityMainBinding
import com.example.searchModule.MovieInfo
import com.example.searchModule.MovieSearchService
import com.example.searchModule.MovieSearchView


class MainActivity: AppCompatActivity(), MovieSearchView {
    lateinit var  binding: ActivityMainBinding
    private lateinit var  movieSearchService: MovieSearchService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        movieSearchService = MovieSearchService()
        movieSearchService.setMovieSearchView(this)

        val id = "SpMu3WQ4pQo_Kq4KAFcc"
        val pw = "B91xzc8P0c"

        binding.mainSearchBtn.setOnClickListener {
            val searchingText = binding.mainSearchEt.text.toString()
            movieSearchService.movieSearch(id, pw, searchingText)
            Log.d("전송",id)
        }

        val movieRVAdapter = MovieRVAdapter(this)
        binding.mainMoviesRv.adapter = movieRVAdapter
        binding.mainMoviesRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun onMovieSearchSuccess(message: String, result: ArrayList<MovieInfo>) {
        Log.d("결과",result.toString())
    }

    override fun onMovieSearchFailure(code: Int, message: String) {
        Log.d("결과",code.toString())
    }
}