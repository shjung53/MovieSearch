package com.example.movieSearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieSearch.databinding.ItemLogBinding
import java.util.*

class LogRVAdapter() : ListAdapter<String, LogRVAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLogBinding =
            ItemLogBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogRVAdapter.ViewHolder, position: Int) {
        val log = getItem(position) as String
        holder.bind(log)
    }


    inner class ViewHolder(private val binding: ItemLogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(log: String) {
            binding.itemLogTv.text = log
        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {

            //            같은 아이템인지 체크
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            //            같은 내용인지 체크 위에서 ture로 통과하면 실행
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }

    }
}