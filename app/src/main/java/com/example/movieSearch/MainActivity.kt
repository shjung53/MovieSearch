package com.example.movieSearch
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val movieRVAdapter = MovieRVAdapter(this)
        binding.mainMoviesRv.adapter = movieRVAdapter
        binding.mainMoviesRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}