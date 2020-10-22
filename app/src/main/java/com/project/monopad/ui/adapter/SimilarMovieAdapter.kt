package com.project.monopad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.databinding.SimilarMovieViewBinding
import com.project.monopad.model.network.response.MovieInfoResultResponse

class SimilarMovieAdapter : RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder>() {

    private var similarMovieList = ArrayList<MovieInfoResultResponse>()

    private var listener: ((id: Int) -> Unit)? = null

    fun setOnSimilarClickListener(listener: (id: Int) -> Unit){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: SimilarMovieViewBinding = SimilarMovieViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarMovieAdapter.ViewHolder, position: Int) {
        holder.bindView(similarMovieList[position])
        holder.onClick(similarMovieList[position])
    }

    override fun getItemCount(): Int {
        return similarMovieList.size
    }

    fun setList(list: List<MovieInfoResultResponse>){
        this.similarMovieList = list as ArrayList<MovieInfoResultResponse>
    }
    inner class ViewHolder(private val binding: SimilarMovieViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(movie: MovieInfoResultResponse){
            binding.movie = movie
        }

        fun onClick(movie: MovieInfoResultResponse){
            binding.ivDetailSimilar.setOnClickListener{
                listener?.invoke(movie.id)
            }
        }
    }

}