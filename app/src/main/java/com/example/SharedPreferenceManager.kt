package com.example

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.util.*

class SharedPreferenceManager(context: Context) {

    val spf = context.getSharedPreferences("searchLog", AppCompatActivity.MODE_PRIVATE)
    //검색어 배열을 Json으로 변환해 String타입으로 저장

    fun saveSearchLog(searchLogs: LinkedList<String>) {
        val editor = spf.edit()
        val gson = Gson()
        val log = gson.toJson(searchLogs)

        editor.putString("log", log)
        editor.apply()
    }

    //Json 형태의 String으로 꺼냄 (액티비티에서 배열로 전환 필요)
    fun getSearchLog(): String? {
        val log = spf.getString("log", "")
        return log!!
    }

}


