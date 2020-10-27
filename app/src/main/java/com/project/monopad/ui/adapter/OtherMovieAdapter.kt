package com.project.monopad.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.R
import com.project.monopad.databinding.RecommendMovieViewBinding
import com.project.monopad.databinding.SimilarMovieViewBinding
import com.project.monopad.model.network.response.MovieInfoResultResponse
import com.project.monopad.ui.adapter.home.MovieItemView
import com.project.monopad.util.OtherMovieCase

class OtherMovieAdapter(private val otherMovieCase: OtherMovieCase) : RecyclerView.Adapter<MovieItemView>() {

    private var otherMovieList = ArrayList<MovieInfoResultResponse>()

    private var listener: ((id: Int) -> Unit)? = null

    fun setOnOtherClickListener(listener: (id: Int) -> Unit){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemView {
        val inflate = {id: Int -> LayoutInflater.from(parent.context).inflate(id, parent, false) }

        return when(otherMovieCase){
            OtherMovieCase.SIMILAR -> {
                SimilarViewHolder(inflate(R.layout.similar_movie_view))
            }
            OtherMovieCase.RECOMMEND -> {
                RecommendViewHolder(inflate(R.layout.recommend_movie_view))
            }
        }
    }

    override fun onBindViewHolder(holder: MovieItemView, position: Int) {
        when(holder) {
            is SimilarViewHolder -> {
                holder.bindView(otherMovieList[position])
                holder.onClick(otherMovieList[position])
            }
            is RecommendViewHolder -> {
                holder.bindView(otherMovieList[position])
                holder.onClick(otherMovieList[position])
            }
        }
    }

    override fun getItemCount() = otherMovieList.size

    fun setList(list: List<MovieInfoResultResponse>){
        this.otherMovieList = list as ArrayList<MovieInfoResultResponse>
    }

    inner class SimilarViewHolder(@NonNull itemView: View) : MovieItemView(itemView){
        var binding = DataBindingUtil.bind<SimilarMovieViewBinding>(itemView)

        fun bindView(movie: MovieInfoResultResponse){
            binding!!.movie = movie
        }

        fun onClick(movie: MovieInfoResultResponse){
            binding!!.ivDetailSimilar.setOnClickListener{
                listener?.invoke(movie.id)
            }
        }
    }

    inner class RecommendViewHolder(@NonNull itemView: View) : MovieItemView(itemView){
        val binding = DataBindingUtil.bind<RecommendMovieViewBinding>(itemView)

        fun bindView(movie: MovieInfoResultResponse){
            binding!!.movie = movie
        }

        fun onClick(movie: MovieInfoResultResponse){
            binding!!.ivDetailRecommend.setOnClickListener{
                listener?.invoke(movie.id)
            }
        }
    }
}