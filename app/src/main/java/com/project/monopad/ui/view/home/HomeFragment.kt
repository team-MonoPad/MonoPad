package com.project.monopad.ui.view.home

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.FragmentHomeBinding
import com.project.monopad.model.network.response.MovieInfoResultResponse
import com.project.monopad.ui.base.BaseFragment

import com.project.monopad.ui.adapter.home.MovieAdapter
import com.project.monopad.ui.adapter.home.MovieCase

import com.project.monopad.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, MovieViewModel>() {

    override val viewModel: MovieViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    private val popularAdapter = MovieAdapter(MovieCase.POPULAR)
    private val nowPlayingAdapter = MovieAdapter(MovieCase.NOW_PLAYING)
    private val topRatedAdapter = MovieAdapter(MovieCase.TOP_RATED)
    private val upcomingAdapter = MovieAdapter(MovieCase.UPCOMING)

    override fun initStartView() {
        adapterSetting()
    }

    override fun initDataBinding() {
        viewModel.popularMovieData()
        viewModel.nowPlayMovieData()
        viewModel.upcomingMovieData()
        viewModel.topRatedMovieData()
    }

    override fun initAfterBinding() {
        observeMovieLiveData()
    }

    private fun observeMovieLiveData(){
        viewModel.popularMovieData.observe(this, Observer<List<MovieInfoResultResponse>>{
            popularAdapter.setMovies(it)
            viewDataBinding.homeViewpager.adapter = popularAdapter
        })

        viewModel.nowPlayingMovieData.observe(this, Observer<List<MovieInfoResultResponse>>{
            nowPlayingAdapter.setMovies(it)
            viewDataBinding.homeRvNowPlaying.adapter = nowPlayingAdapter
        })

        //region 추가
        viewModel.upcomingMovieData.observe(this, Observer<List<MovieInfoResultResponse>>{
            upcomingAdapter.setMovies(it)
            viewDataBinding.homeRvUpcoming.adapter = upcomingAdapter
        })

        viewModel.topRatedMovieData.observe(this, Observer<List<MovieInfoResultResponse>>{
            topRatedAdapter.setMovies(it)
            viewDataBinding.homeRvTopRated.adapter = topRatedAdapter
        })
    }

   private fun adapterSetting() {
       //Set LayoutManager
       viewDataBinding.homeRvNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
       viewDataBinding.homeRvUpcoming.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
       viewDataBinding.homeRvTopRated.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

       //Set Click Listener
       popularAdapter.setOnTrailerClickListener {
           Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
//           startActivity(Intent(activity, VideoActivity.class::java))
       }

       nowPlayingAdapter.setOnItemClickListener {
           Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
       }

       topRatedAdapter.setOnItemClickListener {
           Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
           Navigation.findNavController(requireView()).navigate(R.id.action_home_to_detail)
       }

       upcomingAdapter.setOnItemClickListener {
           Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
       }

    }


}