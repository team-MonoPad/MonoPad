package com.project.monopad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.databinding.TrailerViewBinding
import com.project.monopad.data.model.network.response.MovieVideoResultResponse

class TrailerAdapter : RecyclerView.Adapter<TrailerAdapter.ViewHolder>() {

    private var trailerList = ArrayList<MovieVideoResultResponse>()

    private var listener: ((key: String) -> Unit)? = null

    fun setOnTrailerClickListener(listener: (key: String) -> Unit){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: TrailerViewBinding = TrailerViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(trailerList[position])
        holder.onClick(trailerList[position])
    }

    override fun getItemCount(): Int {
        return trailerList.size
    }

    fun setList(list: List<MovieVideoResultResponse>){
        this.trailerList = list as ArrayList<MovieVideoResultResponse>
    }

    inner class ViewHolder(private val binding: TrailerViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(video: MovieVideoResultResponse){
            binding.video = video
        }

        fun onClick(video: MovieVideoResultResponse){
            binding.ivDetailTrailer.setOnClickListener{
                listener?.invoke(video.key)
            }
        }
    }
}