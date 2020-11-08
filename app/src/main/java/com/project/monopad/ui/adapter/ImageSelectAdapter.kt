package com.project.monopad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.project.monopad.databinding.ImageSelectViewBinding
import com.project.monopad.data.model.network.response.MovieImagePosterResponse

class ImageSelectAdapter : RecyclerView.Adapter<ImageSelectAdapter.ViewHolder>() {

    private var imageList = ArrayList<MovieImagePosterResponse>()

    private var checkBox: CheckBox? = null
    private var saveImagePath: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ImageSelectViewBinding = ImageSelectViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(imageList[position])
        holder.clickCheckBox()
        holder.clickImageView()
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

        fun clickImageView(){
            binding.ivImageSelect.setOnClickListener{
                binding.cbImageSelect.also {
                    it.isChecked = !it.isChecked
                    clickEvent(it)
                }
            }
        }

        fun clickCheckBox(){
            binding.cbImageSelect.setOnClickListener{ view ->
                clickEvent(view as CheckBox)
            }
        }

        private fun clickEvent(cb : CheckBox){
            if(checkBox != null) {
                if (checkBox != cb) {
                    checkBox!!.isChecked = false;
                }
            }
            checkBox =  cb
            saveImagePath = if(checkBox!!.isChecked) binding.image!!.file_path else null
        }
    }

}