package com.project.monopad.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.monopad.R
import com.project.monopad.extension.dDay
import com.project.monopad.model.network.response.MovieInfoResultResponse
import jp.wasabeef.glide.transformations.BlurTransformation

//https://lakue.tistory.com/16?category=853542
class MovieAdapter(private val movieCase: MovieCase) : RecyclerView.Adapter<MovieItemView>() {
    val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
    private var movies: ArrayList<MovieInfoResultResponse> = ArrayList()

    private var listener: ((id: Int) -> Unit)? = null

    fun setOnTrailerClickListener(listener: (id: Int) -> Unit) {
        this.listener = listener
    }
    fun setOnItemClickListener(listener: (id: Int) -> Unit) {
        this.listener = listener
    }

    fun setMovies(movies: ArrayList<MovieInfoResultResponse>){
        this.movies = movies
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
            val moviePosterURL = POSTER_BASE_URL + movie.poster_path
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(binding!!.itemMovieIvPoster)

            Glide.with(itemView.context)
                .load(moviePosterURL)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(13,3)))
                .into(binding!!.itemMovieIvBlurPoster)

            binding!!.itemMovieTvTitle.text = movie.title
            binding!!.itemMovieTv1.text = movie.release_date.split("-")[0] //연도

            binding!!.itemMovieBtTrailer.setOnClickListener {
                listener?.invoke(movie.id)
            }

        }
    }

    inner class NowPlayingMovieViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemNowPlayingBinding? = DataBindingUtil.bind(itemView)

        fun bind(movie: MovieInfoResultResponse){
            val moviePosterURL = POSTER_BASE_URL + movie.poster_path
            binding!!.itemNowIvPoster.apply {
                background = ContextCompat.getDrawable(itemView.context, R.drawable.image_shape) //이미지 라운딩 처리
                clipToOutline = true
            }
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(binding!!.itemNowIvPoster)

            binding!!.itemNowTvTitle.text = movie.title

            itemView.setOnClickListener {
                listener?.invoke(movie.id)
            }
        }
    }

    inner class UpcomingViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemUpcomingBinding? = DataBindingUtil.bind(itemView)

        fun bind(movie: MovieInfoResultResponse){
            val moviePosterURL = POSTER_BASE_URL + movie.poster_path
            //이미지뷰 라운딩 처리
            binding!!.itemUpcomingIvPoster.apply {
                background = ContextCompat.getDrawable(itemView.context, R.drawable.image_shape) //이미지 라운딩 처리
                clipToOutline = true
            }
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(binding!!.itemUpcomingIvPoster)
//            d("dday", movie.release_date)
            binding!!.itemUpcomingTvDDay.text = dDay(movie.release_date)

            itemView.setOnClickListener {
                listener?.invoke(movie.id)
            }
        }
    }

    inner class TopRatedViewHolder(@NonNull itemView: View) : MovieItemView(itemView) {
        var binding: ItemTopRatedBinding? = DataBindingUtil.bind(itemView)

        fun bind(position:Int, movie: MovieInfoResultResponse){
            val moviePosterURL = POSTER_BASE_URL + movie.poster_path

            //이미지뷰 라운딩 처리
            binding!!.itemTopRatedIvPoster.apply {
                background = ContextCompat.getDrawable(itemView.context, R.drawable.image_shape) //이미지 라운딩 처리
                clipToOutline = true
            }
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(binding!!.itemTopRatedIvPoster)

            binding!!.itemTopRatedTvRanking.text = (position+1).toString()
            binding!!.itemTopRatedTvTitle.text = movie.title
//            d("top rated", .vote_count.toString())

            itemView.setOnClickListener {
                listener?.invoke(movie.id)
            }
        }
    }



}