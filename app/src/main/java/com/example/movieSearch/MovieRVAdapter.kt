package com.example.movieSearch
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        val binding = DataBindingUtil.inflate<ItemMovieBinding>(LayoutInflater.from(viewGroup.context),R.layout.item_movie, viewGroup, false)

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
            binding.title = movieInfo.title.parseAsHtml().toString()
            binding.movieInfo = movieInfo
            ImgBinding
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