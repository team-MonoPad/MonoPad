package com.project.monopad.ui.viewmodel

import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.base.BaseViewModel

class DetailViewModel(private val repo: MovieRepoImpl) : BaseViewModel() {

    lateinit var title: String
    fun init(){
        title = "안녕하세요"
    }
}