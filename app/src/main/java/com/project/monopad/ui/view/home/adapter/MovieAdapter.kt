package com.project.monopad.ui.view.home.adapter

import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.monopad.R
import com.project.monopad.databinding.ItemHomeMovieBinding
import com.project.monopad.databinding.ItemPopularBinding
import com.project.monopad.databinding.ItemTopRatedBinding
import com.project.monopad.databinding.ItemUpcomingBinding
import com.project.monopad.extension.dDay
import com.project.monopad.model.network.response.MovieInfoResultResponse
import kotlinx.android.synthetic.main.item_popular.view.*
import kotlinx.android.synthetic.main.item_home_movie.view.*
import kotlinx.android.synthetic.main.item_top_rated.view.*
import kotlinx.android.synthetic.main.item_upcoming.view.*

//https://lakue.tistory.com/16?category=853542
class MovieAdapter(private val movieCase: MovieCase, private val movies: ArrayList<MovieInfoResultResponse>) : RecyclerView.Adapter<MovieItemView>() {
    val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
//    private val movies: ArrayList<MovieInfoResultResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemView {
        val view: View

        when (movieCase){
            MovieCase.POPULAR -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_popular,
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
                    R.layout.item_home_movie,
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
        var binding: ItemPopularBinding? = DataBindingUtil.bind(itemView)

        fun bind(movie: MovieInfoResultResponse){
            val moviePosterURL = POSTER_BASE_URL + movie.poster_path
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.item_movie_iv_poster)
            binding!!.itemMovieTvTitle.text = movie.title
        }
    }

    fun setData(data : MovieInfoResultResponse){
        movies.add(data)
    }

    inner class NowPlayingMovieViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemHomeMovieBinding? = DataBindingUtil.bind(itemView)

        fun bind(movie: MovieInfoResultResponse){
            val moviePosterURL = POSTER_BASE_URL + movie.poster_path
            Log.d("nowplaying poster", moviePosterURL)
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.item_home_iv_movie)
        }
    }

    inner class UpcomingViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemUpcomingBinding? = DataBindingUtil.bind(itemView)

        fun bind(movie: MovieInfoResultResponse){
            val moviePosterURL = POSTER_BASE_URL + movie.poster_path
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.item_upcoming_iv_poster)

            binding!!.itemUpcomingTvDDay.text = dDay(movie.release_date)
        }
    }

    inner class TopRatedViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemTopRatedBinding? = DataBindingUtil.bind(itemView)

        fun bind(position:Int, movie: MovieInfoResultResponse){
            val moviePosterURL = POSTER_BASE_URL + movie.poster_path
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.item_top_rated_iv_poster)

            binding!!.itemTopRatedTvRanking.text = position.toString()
            binding!!.itemTopRatedTvTitle.text = movie.title
            d("top rated", movie.vote_count.toString())
        }
    }

}