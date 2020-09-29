package com.project.monopad.ui.view.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.monopad.R
import com.project.monopad.databinding.ActivityMainBinding
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiaryFragment : BaseFragment<ActivityMainBinding, MovieViewModel>() {

    override val viewModel: MovieViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_diary



    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}