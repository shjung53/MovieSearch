package com.example.movieSearch
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieSearch.databinding.ActivityMainBinding
import com.example.searchModule.MovieInfo
import com.example.searchModule.MovieSearchResponse
import com.example.searchModule.MovieSearchService
import com.example.searchModule.MovieSearchView



class MainActivity: AppCompatActivity(), MovieSearchView {
    lateinit var  binding: ActivityMainBinding
    private lateinit var  movieSearchService: MovieSearchService
    private lateinit var movieRVAdapter: MovieRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieInfo = ArrayList<MovieInfo>()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        movieSearchService = MovieSearchService()
        movieSearchService.setMovieSearchView(this)

        val clientId = "KvfkPaq5V52MCqyYYmUc"
        val clientSecret = "fnPfJB7Rwl"

        binding.mainSearchBtn.setOnClickListener {
            val searchingText = binding.mainSearchEt.text.toString()
            movieSearchService.movieSearch(clientId, clientSecret, searchingText)
        }

        movieRVAdapter = MovieRVAdapter(this,movieInfo)
        binding.mainMoviesRv.adapter = movieRVAdapter
        binding.mainMoviesRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun onMovieSearchSuccess(result: MovieSearchResponse) {
        if(result.items != null){
            movieRVAdapter.update(result.items!!)
        }else{
            Toast.makeText(this,"검색 결과가 없습니다",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMovieSearchFailure() {
        Toast.makeText(this, "오류가 발생했습니다 다시 시도해주세요",Toast.LENGTH_SHORT).show()
    }
}