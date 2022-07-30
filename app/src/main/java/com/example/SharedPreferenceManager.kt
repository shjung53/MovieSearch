package com.example

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.movieSearch.STRING_NULL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
class SharedPreferenceManager(context: Context) {

    private val LOG_KEY = "log"

    private val spf = context.getSharedPreferences("searchLog", AppCompatActivity.MODE_PRIVATE)
    private val editor = spf.edit()!!
    private val gson = Gson()
    private val queueType = object : TypeToken<LinkedList<String>>() {}.type!!
    private val log = spf.getString(LOG_KEY, STRING_NULL)!!
    //        저장된 기록이 있으면 동기화 없으면 빈 queue
    val searchLogs = if(log != ""){gson.fromJson(log, queueType)}else{
        LinkedList<String>()
    }

    //검색어 배열을 Json으로 변환해 String타입으로 저장
    fun saveSearchLog(searchLogs: LinkedList<String>) {
        editor.putString(LOG_KEY, gson.toJson(searchLogs))
        editor.apply()
    }
}


