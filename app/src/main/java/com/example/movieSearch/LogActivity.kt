package com.example.movieSearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SharedPreferenceManager
import com.example.movieSearch.databinding.ActivityLogBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
        val gson = Gson()
        val queueType =  object : TypeToken<LinkedList<String>>() {}.type
        val log = sharedPreferenceManager.getSearchLog()
//        저장된 기록이 있으면 동기화 없으면 빈 queue
        val searchLogs = if(log != ""){gson.fromJson(log, queueType)}else{
            LinkedList<String>()
        }
        logRVAdapter.submitList(searchLogs)


        setContentView(binding.root)
    }
}