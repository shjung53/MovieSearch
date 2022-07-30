package com.example.movieSearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SharedPreferenceManager
import com.example.movieSearch.databinding.ActivityLogBinding

const val SEARCH_KEY = "logText"
class LogActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLogBinding
    private lateinit var logRVAdapter: LogRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding.inflate(layoutInflater)

//        어댑터 설정
        logRVAdapter = LogRVAdapter()
        binding.logRv.adapter = logRVAdapter
        binding.logRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val sharedPreferenceManager = SharedPreferenceManager(this)
        val searchLogs = sharedPreferenceManager.searchLogs
        logRVAdapter.submitList(searchLogs)

        logRVAdapter.setLogClickListener(object: LogRVAdapter.LogClickListener{
            override fun clickLog(holder: LogRVAdapter.ViewHolder, position: Int) {
                holder.binding.itemLogTv.setOnClickListener {
                    val intent = Intent(this@LogActivity, MainActivity::class.java)
                    val searchingText = logRVAdapter.currentList[holder.adapterPosition].toString()
                    intent.putExtra(SEARCH_KEY,searchingText)
                    startActivity(intent)
                    finish()
                }
            }
        })



        setContentView(binding.root)
    }
}