package com.example.movieSearch
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SharedPreferenceManager
import com.example.movieSearch.databinding.ActivityMainBinding
import com.example.searchModule.MovieSearchResponse
import com.example.searchModule.MovieSearchService
import com.example.searchModule.MovieSearchView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class MainActivity: AppCompatActivity(), MovieSearchView {
    lateinit var  binding: ActivityMainBinding
    private lateinit var  movieSearchService: MovieSearchService
    private lateinit var movieRVAdapter: MovieRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieSearchService = MovieSearchService()
        movieSearchService.setMovieSearchView(this)

//        네이버 api 아이디, 비번
        val clientId = "KvfkPaq5V52MCqyYYmUc"
        val clientSecret = "fnPfJB7Rwl"

//        검색 기록 queue
        val sharedPreferenceManager = SharedPreferenceManager(this)
        val gson = Gson()
        val queueType =  object : TypeToken<LinkedList<String>>() {}.type
        val log = sharedPreferenceManager.getSearchLog()
//        저장된 기록이 있으면 동기화 없으면 빈 queue
        val searchLogs = if(log != ""){gson.fromJson(log, queueType)}else{
            LinkedList<String>()
        }

//        검색 클릭리스너
        binding.mainSearchBtn.setOnClickListener {
            val searchingText = binding.mainSearchEt.text.toString()
//            검색 기록 중복 확인
            for(i in 0 until searchLogs.size){
                if(searchLogs[i] == searchingText){
                    searchLogs.removeAt(i)
                    break
                }
            }
//            검색기록 밀어내기
            if(searchLogs.size==10){
                searchLogs.pop()
            }
//            저장
            searchLogs.offer(searchingText)
            sharedPreferenceManager.saveSearchLog(searchLogs)
            movieSearchService.movieSearch(clientId, clientSecret, searchingText)
        }

//        영화 어댑터
        movieRVAdapter = MovieRVAdapter()
        binding.mainMoviesRv.adapter = movieRVAdapter
        binding.mainMoviesRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

//        영화 클릭시 브라우저 연결
        movieRVAdapter.setItemClickListener(object: MovieRVAdapter.ItemClickListener{
            override fun clickMovie(holder: MovieRVAdapter.ViewHolder, position: Int) {
                holder.binding.itemMovieCl.setOnClickListener {
                    val url = movieRVAdapter.currentList[position].link
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }

        })

    }

    override fun onMovieSearchSuccess(result: MovieSearchResponse) {
//        검색 결과가 없으면 null이 아니라 사이즈 0인 배열로 옴
        if(result.items!!.size>0){
            movieRVAdapter.submitList(result.items!!)
        }else{
            Toast.makeText(this,"검색 결과가 없습니다",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMovieSearchFailure() {
        Toast.makeText(this, "오류가 발생했습니다 다시 시도해주세요",Toast.LENGTH_SHORT).show()
    }

}