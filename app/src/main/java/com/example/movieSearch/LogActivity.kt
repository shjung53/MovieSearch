package com.example.movieSearch

import android.content.Intent
import android.os.Bundle
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

//        검색기록 queue
        val sharedPreferenceManager = SharedPreferenceManager(this)
        val searchLogs = sharedPreferenceManager.searchLogs

//        어댑터 설정
        logRVAdapter = LogRVAdapter()
        binding.logRv.adapter = logRVAdapter
        binding.logRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        logRVAdapter.submitList(searchLogs)

//        검색어 클릭 이벤트, MainActivity로 넘어가서 해당 키워드로 검색
        logRVAdapter.setLogClickListener(object: LogRVAdapter.LogClickListener{
            override fun clickLog(holder: LogRVAdapter.ViewHolder, position: Int) {
                holder.binding.itemLogTv.setOnClickListener {
                    val intent = Intent(this@LogActivity, MainActivity::class.java)
                    val searchingText = logRVAdapter.currentList[holder.adapterPosition].toString()
                    intent.putExtra(SEARCH_KEY,searchingText)
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }
        })



        setContentView(binding.root)
    }
}