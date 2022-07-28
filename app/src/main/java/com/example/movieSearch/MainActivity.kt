package com.example.movieSearch
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieSearch.databinding.ActivityMainBinding
import com.example.searchModule.MovieSearchService
import com.example.searchModule.MovieSearchView
import com.example.searchModule.Result
import okio.ByteString.Companion.encodeUtf8


class MainActivity: AppCompatActivity(), MovieSearchView {
    lateinit var  binding: ActivityMainBinding
    private lateinit var  movieSearchService: MovieSearchService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        movieSearchService = MovieSearchService()
        movieSearchService.setMovieSearchView(this)

        val clientId = "r1v9bwMSFGBYScAlQe_r"
        val clientSecret = "nRi7rDBBgB"

        binding.mainSearchBtn.setOnClickListener {
            val searchingText = binding.mainSearchEt.text.toString()
            movieSearchService.movieSearch(clientId, clientSecret, searchingText.encodeUtf8())
        }

        val movieRVAdapter = MovieRVAdapter(this)
        binding.mainMoviesRv.adapter = movieRVAdapter
        binding.mainMoviesRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun onMovieSearchSuccess(result: Result) {
    }

    override fun onMovieSearchFailure(code: Int) {
    }
}