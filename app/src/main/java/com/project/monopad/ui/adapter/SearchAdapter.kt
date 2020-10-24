package com.project.monopad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.databinding.ItemSearchMovieBinding
import com.project.monopad.model.network.response.MovieInfoResultResponse

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var movieList = ArrayList<MovieInfoResultResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemSearchMovieBinding = ItemSearchMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setList(list: List<MovieInfoResultResponse>){
        this.movieList = list as ArrayList<MovieInfoResultResponse>
    }

    inner class ViewHolder(private val binding: ItemSearchMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(movie: MovieInfoResultResponse){
            binding.movie = movie
        }
    }

}