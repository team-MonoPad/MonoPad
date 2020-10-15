package com.project.monopad.ui.view.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.R
import com.project.monopad.databinding.ImageSelectViewBinding
import com.project.monopad.model.network.response.MovieImagePosterResponse

class ImageSelectAdapter : RecyclerView.Adapter<ImageSelectAdapter.ViewHolder>() {

    private var imageList = ArrayList<MovieImagePosterResponse>()

    private var check = false
    private var saveImagePath: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ImageSelectViewBinding = ImageSelectViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(imageList[position])
        holder.clickCheckBox()
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun setList(list: List<MovieImagePosterResponse>){
        this.imageList = list as ArrayList<MovieImagePosterResponse>
    }

    fun getImagePath() = saveImagePath

    inner class ViewHolder(private val binding: ImageSelectViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(image: MovieImagePosterResponse){
            binding.image = image
        }

        fun clickCheckBox(){
            binding.cbImageSelect.setOnClickListener{
                binding.cbImageSelect.also {
                    if(!it.isChecked){ // 선택되어있을때
                        it.isChecked = false
                        check = false
                        saveImagePath = null
                    } else { // 선택 안되어있을때
                        if(!check){ // 다른게 선택 안되어있다면
                            it.isChecked = true
                            check = true
                            saveImagePath = binding.image!!.file_path
                        } else { // 다른게 선택되어 있다면
                            Toast.makeText(it.context, R.string.image_select_checkbox, Toast.LENGTH_SHORT).show()
                            it.isChecked = false

                        }
                    }
                }
            }
        }
    }

}