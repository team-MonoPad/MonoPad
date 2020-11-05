package com.project.monopad.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.databinding.ItemSearchMovieBinding
import com.project.monopad.model.network.response.MovieInfoResultResponse

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var movieList = ArrayList<MovieInfoResultResponse>()

    private var listener: ((id: Int) -> Unit)? = null
    private var touchListener: (() -> Unit)? = null

    fun setOnSearchClickListener(listener: (id: Int) -> Unit){
        this.listener = listener
    }
    fun setOnSearchTouchListener(listener: () -> Unit){
        this.touchListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemSearchMovieBinding = ItemSearchMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(movieList[position])
        holder.onClick(movieList[position])
        holder.onTouch()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setList(list: List<MovieInfoResultResponse>){
        this.movieList = list as ArrayList<MovieInfoResultResponse>
    }

    fun refreshList(){
        this.movieList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSearchMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(movie: MovieInfoResultResponse){
            binding.movie = movie
        }

        fun onClick(movie: MovieInfoResultResponse){
            binding.ivSearchPoster.setOnClickListener {
                listener?.invoke(movie.id)
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        fun onTouch(){
            binding.ivSearchPoster.setOnTouchListener { view, motionEvent ->
                when(motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        touchListener?.invoke()
                        false
                    }
                    else -> false
                }
            }
        }
    }

}
