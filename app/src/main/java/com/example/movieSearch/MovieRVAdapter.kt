package com.example.movieSearch
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieSearch.databinding.ItemMovieBinding
import com.example.searchModule.MovieInfo


class MovieRVAdapter(): ListAdapter<MovieInfo, MovieRVAdapter.ViewHolder>(diffUtil) {

    interface MovieClickListener{
        fun clickMovie(holder: ViewHolder, position: Int)
    }

    private lateinit var movieClickListener: MovieClickListener

    fun setItemClickListener(pMovieClickListener: MovieClickListener){
        movieClickListener = pMovieClickListener
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieRVAdapter.ViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieRVAdapter.ViewHolder, position: Int) {
        val movieInfo = getItem(position) as MovieInfo
        holder.bind(movieInfo)

        movieClickListener.clickMovie(holder, position)
    }

    inner class ViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieInfo: MovieInfo) {
//            parseAsHtml로 태그 제거
            binding.itemMovieTitleTv2.text = movieInfo.title.parseAsHtml()
            binding.itemMovieReleaseTv2.text = movieInfo.pubDate
            binding.itemMovieRateTv2.text = movieInfo.userRating
            Glide.with(binding.itemMoviePosterIv).load(movieInfo.image).into(binding.itemMoviePosterIv)
        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MovieInfo>() {

//            같은 아이템인지 체크
            override fun areItemsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean {
                return oldItem.title == newItem.title
            }

//            같은 내용인지 체크 위에서 true로 통과하면 실행
            override fun areContentsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean {
                return oldItem == newItem
            }
        }

    }
}