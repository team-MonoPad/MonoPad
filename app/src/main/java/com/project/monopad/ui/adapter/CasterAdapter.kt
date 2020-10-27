package com.project.monopad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.databinding.CasterViewBinding
import com.project.monopad.model.network.response.MovieCastResponse

class CasterAdapter : RecyclerView.Adapter<CasterAdapter.ViewHolder>() {

    private var casterList = ArrayList<MovieCastResponse>()

    private var listener: ((id: Int) -> Unit)? = null

    fun setOnCasterClickListener(listener: (id: Int) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CasterAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: CasterViewBinding = CasterViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CasterAdapter.ViewHolder, position: Int) {
        holder.bindView(casterList[position])
        holder.onClick(casterList[position])
    }

    override fun getItemCount(): Int {
        return casterList.size
    }

    fun setList(list: List<MovieCastResponse>){
        this.casterList = list as ArrayList<MovieCastResponse>
    }

    inner class ViewHolder(private val binding: CasterViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(cast: MovieCastResponse){
            binding.caster = cast
        }

        fun onClick(cast: MovieCastResponse){
            binding.ivDetailCaster.setOnClickListener{
                listener?.invoke(cast.id)
            }
        }
    }
}