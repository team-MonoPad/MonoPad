package com.project.monopad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.databinding.RecommendMovieViewBinding
import com.project.monopad.model.network.response.MovieInfoResultResponse

class RecommendMovieAdapter : RecyclerView.Adapter<RecommendMovieAdapter.ViewHolder>() {

    private var recommendMovieList = ArrayList<MovieInfoResultResponse>()

    private var listener: ((id: Int) -> Unit)? = null

    fun setOnRecommendClickListener(listener: (id: Int) -> Unit){
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendMovieAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: RecommendMovieViewBinding = RecommendMovieViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendMovieAdapter.ViewHolder, position: Int) {
        holder.bindView(recommendMovieList[position])
        holder.onClick(recommendMovieList[position])
    }

    override fun getItemCount(): Int {
        return recommendMovieList.size
    }

    fun setList(list: List<MovieInfoResultResponse>){
        this.recommendMovieList = list as ArrayList<MovieInfoResultResponse>
    }
    inner class ViewHolder(private val binding: RecommendMovieViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(movie: MovieInfoResultResponse){
            binding.movie = movie
        }

        fun onClick(movie: MovieInfoResultResponse){
            binding.ivDetailRecommend.setOnClickListener{
                listener?.invoke(movie.id)
            }
        }
    }
}