package com.example.movieSearch

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieSearch.databinding.ItemMovieBinding
import com.example.searchModule.MovieInfo

class MovieRVAdapter(val context:Context, private var movieInfo: ArrayList<MovieInfo>): RecyclerView.Adapter<MovieRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieRVAdapter.ViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieRVAdapter.ViewHolder, position: Int) {
        holder.bind(movieInfo[position])
    }

    override fun getItemCount()= movieInfo.size

    inner class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movieInfo: MovieInfo) {
//            parseAsHtml로 태그 제거
            binding.itemMovieTitleTv.text = movieInfo.title.parseAsHtml()
            binding.itemMovieReleaseTv.text = movieInfo.pubDate
            binding.itemMovieRateTv.text = movieInfo.userRating
            Glide.with(binding.itemMoviePosterIv).load(movieInfo.image).into(binding.itemMoviePosterIv)
        }
    }


    fun update(result: ArrayList<MovieInfo>){
        movieInfo.clear()
        movieInfo = result
        notifyItemChanged(0)
    }

}