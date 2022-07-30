package com.example.movieSearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieSearch.databinding.ActivityLogBinding

class LogActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding.inflate(layoutInflater)



        setContentView(binding.root)
    }
}