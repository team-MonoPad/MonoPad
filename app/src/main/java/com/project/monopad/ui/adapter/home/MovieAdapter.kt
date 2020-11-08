package com.project.monopad.ui.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.R
import com.project.monopad.databinding.ItemNowPlayingBinding
import com.project.monopad.databinding.ItemPopularPageBinding
import com.project.monopad.databinding.ItemTopRatedBinding
import com.project.monopad.databinding.ItemUpcomingBinding
import com.project.monopad.extension.dDay
import com.project.monopad.model.network.response.MovieInfoResultResponse

class MovieAdapter(private val movieCase: MovieCase) : RecyclerView.Adapter<MovieItemView>() {

    private var movies: ArrayList<MovieInfoResultResponse> = ArrayList()

    private var listener: ((id: Int) -> Unit)? = null
    private var trailerListener: ((id: Int) -> Unit)? = null

    fun setOnTrailerClickListener(listener: (id: Int) -> Unit) {
        this.trailerListener = listener
    }

    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        this.listener = listener
    }

    fun setMovies(movies: List<MovieInfoResultResponse>){
        this.movies = movies as ArrayList<MovieInfoResultResponse>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemView {
        val view: View

        when (movieCase){
            MovieCase.POPULAR -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_popular_page,
                    parent,
                    false
                )
                view.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                return PopularMovieViewHolder(view)
            }

            MovieCase.NOW_PLAYING -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_now_playing,
                    parent,
                    false
                )
                return NowPlayingMovieViewHolder(view)
            }

            MovieCase.UPCOMING -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_upcoming,
                    parent,
                    false
                )
                return UpcomingViewHolder(view)
            }

            MovieCase.TOP_RATED -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_top_rated,
                    parent,
                    false
                )
                return TopRatedViewHolder(view)
            }

        }
    }

    override fun onBindViewHolder(holder: MovieItemView, position: Int) {

        when (holder) {
            is PopularMovieViewHolder -> {
                val popularMovieViewHolder: PopularMovieViewHolder = holder
                popularMovieViewHolder.bind(movies[position])
            }

            is NowPlayingMovieViewHolder -> {
                val nowPlayingMovieViewHolder: NowPlayingMovieViewHolder = holder
                nowPlayingMovieViewHolder.bind(movies[position])
            }

            is UpcomingViewHolder -> {
                val upcomingViewHolder: UpcomingViewHolder = holder
                upcomingViewHolder.bind(movies[position])
            }

            is TopRatedViewHolder -> {
                val topRatedViewHolder: TopRatedViewHolder = holder
                topRatedViewHolder.bind(position, movies[position])
            }
        }
    }

    override fun getItemCount(): Int = movies.size

    inner class PopularMovieViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemPopularPageBinding? = DataBindingUtil.bind(itemView)

        fun bind(movie: MovieInfoResultResponse){
            binding!!.model = movie

            binding!!.itemMovieBtDetail.setOnClickListener{
                listener?.invoke(movie.id)
            }

            binding!!.itemMovieBtTrailer.setOnClickListener {
                trailerListener?.invoke(movie.id)
            }
        }
    }

    inner class NowPlayingMovieViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemNowPlayingBinding? = DataBindingUtil.bind(itemView)

        fun bind(movie: MovieInfoResultResponse){
            binding!!.model = movie

            binding!!.itemNowIvPoster.apply {
                background = ContextCompat.getDrawable(itemView.context, R.drawable.image_shape) //이미지 라운딩 처리
                clipToOutline = true
            }

            itemView.setOnClickListener {
                listener?.invoke(movie.id)
            }
        }
    }

    inner class UpcomingViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemUpcomingBinding? = DataBindingUtil.bind(itemView)

        fun bind(movie: MovieInfoResultResponse){
            binding!!.model = movie

            //이미지뷰 라운딩 처리
            binding!!.itemUpcomingIvPoster.apply {
                background = ContextCompat.getDrawable(itemView.context, R.drawable.image_shape) //이미지 라운딩 처리
                clipToOutline = true
            }

            binding!!.itemUpcomingTvDDay.text = dDay(movie.release_date)

            itemView.setOnClickListener {
                listener?.invoke(movie.id)
            }
        }
    }

    inner class TopRatedViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemTopRatedBinding? = DataBindingUtil.bind(itemView)

        fun bind(position:Int, movie: MovieInfoResultResponse){
            binding!!.model = movie
            binding!!.position = position + 1
            binding!!.itemTopRatedIvPoster.apply {
                background = ContextCompat.getDrawable(itemView.context, R.drawable.image_shape) //이미지 라운딩 처리
                clipToOutline = true
            }

            itemView.setOnClickListener {
                listener?.invoke(movie.id)
            }
        }
    }

}