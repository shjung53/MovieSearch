package com.example.movieSearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SharedPreferenceManager
import com.example.movieSearch.databinding.ActivityLogBinding
import java.util.*

class LogActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLogBinding
    private lateinit var logRVAdapter: LogRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding.inflate(layoutInflater)

        logRVAdapter = LogRVAdapter()
        binding.logRv.adapter = logRVAdapter
        binding.logRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val sharedPreferenceManager = SharedPreferenceManager(this)
        val searchLogs = sharedPreferenceManager.searchLogs
        logRVAdapter.submitList(searchLogs)


        setContentView(binding.root)
    }
}