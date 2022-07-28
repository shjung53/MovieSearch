package com.example.movieSearch

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.ItemMovieBinding

class MovieRVAdapter(val context:Context): RecyclerView.Adapter<MovieRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRVAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MovieRVAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){

        }
    }

}