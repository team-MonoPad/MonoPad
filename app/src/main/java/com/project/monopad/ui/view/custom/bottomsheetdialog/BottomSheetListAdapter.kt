package com.project.monopad.ui.view.custom.bottomsheetdialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.databinding.ItemBsListMovieBinding
import com.project.monopad.model.entity.Review

class BottomSheetListAdapter : RecyclerView.Adapter<BottomSheetListAdapter.ViewHolder>() {

    private var reviews = ArrayList<Review>()
    private var listener: ((id: Int) -> Unit)? = null

    fun setList(reviews : List<Review>) {
        this.reviews = reviews as ArrayList<Review>
        notifyDataSetChanged()
    }

    fun setOnReviewClickListener(listener: (id: Int) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetListAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBsListMovieBinding = ItemBsListMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: BottomSheetListAdapter.ViewHolder, position: Int) {
        holder.bindView(reviews[position])
        holder.onClick(reviews[position])
    }

    inner class ViewHolder(private val binding: ItemBsListMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(review : Review){
            binding.review = review
        }

        fun onClick(review : Review){
            binding.constraintLayout.setOnClickListener{
                listener?.invoke(review.id)
            }
        }
    }

}