package com.project.monopad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.databinding.ItemBsListMovieBinding
import com.project.monopad.data.model.entity.Movie
import com.project.monopad.data.model.entity.Review

class DiaryListBottomSheetAdapter : RecyclerView.Adapter<DiaryListBottomSheetAdapter.ViewHolder>() {

    private var reviews = ArrayList<Review>()
    private var listener: ((movie: Movie) -> Unit)? = null

    fun setList(reviews : List<Review>) {
        this.reviews = reviews as ArrayList<Review>
        notifyDataSetChanged()
    }

    fun setOnReviewClickListener(listener: (movie: Movie) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBsListMovieBinding = ItemBsListMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(reviews[position])
        holder.onClick(reviews[position])
    }

    inner class ViewHolder(private val binding: ItemBsListMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(review : Review){
            binding.review = review
        }

        fun onClick(review : Review){
            binding.constraintLayout.setOnClickListener{
                listener?.invoke(review.movie)
            }
        }
    }

}