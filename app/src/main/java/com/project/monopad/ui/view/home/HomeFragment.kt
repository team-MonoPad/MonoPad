package com.project.monopad.ui.view.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.FragmentHomeBinding
import com.project.monopad.model.network.response.MovieInfoResponse
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.view.home.adapter.MovieAdapter
import com.project.monopad.ui.view.home.adapter.MovieCase
import com.project.monopad.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, MovieViewModel>() {

    override val viewModel: MovieViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

//    private val popularAdapter = MovieAdapter(MovieCase.POPULAR)
//    private val nowPlayingAdapter = MovieAdapter(MovieCase.NOW_PLAYING)
//    private val topRatedAdapter = MovieAdapter(MovieCase.TOP_RATED)
//    private val upcomingAdapter = MovieAdapter(MovieCase.UPCOMING)

    override fun initStartView() {
//        viewDataBinding.homeViewpager.adapter = adapter
//
//        viewDataBinding.homeRvNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        viewDataBinding.homeRvNowPlaying.adapter = adapter
//
//        viewDataBinding.homeRvUpcoming.adapter = adapter
//        viewDataBinding.homeRvUpcoming.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//
//        viewDataBinding.homeRvTopRated.adapter = adapter
//        viewDataBinding.homeRvTopRated.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//
//        viewDataBinding.homeRvLatest.adapter = adapter
//        viewDataBinding.homeRvLatest.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
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

    private fun setViewPagerAdapter(movie: MovieInfoResponse){
        val adapter = MovieAdapter(MovieCase.POPULAR, movie.results)
        viewDataBinding.homeViewpager.adapter = adapter
    }

    private fun setNowPlayMovieAdapter(movie: MovieInfoResponse){
        val adapter = MovieAdapter(MovieCase.NOW_PLAYING, movie.results)
        viewDataBinding.homeRvNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.homeRvNowPlaying.adapter = adapter
    }

    private fun setUpcomingMovieAdapter(movie: MovieInfoResponse){
        val adapter = MovieAdapter(MovieCase.UPCOMING, movie.results)
        viewDataBinding.homeRvUpcoming.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.homeRvUpcoming.adapter = adapter
    }

    private fun setTopRatedMovieAdapter(movie: MovieInfoResponse){
//        movie.results.sort()
        val adapter = MovieAdapter(MovieCase.TOP_RATED, movie.results)
        viewDataBinding.homeRvTopRated.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.homeRvTopRated.adapter = adapter
    }


    private fun observeMovieLiveData(){
        viewModel.livePopularMovie.observe(this, Observer<MovieInfoResponse>{
            setViewPagerAdapter(it)
        })

        viewModel.liveNowPlayingMovie.observe(this, Observer<MovieInfoResponse>{
            setNowPlayMovieAdapter(it)
        })

        viewModel.liveUpComingMovie.observe(this, Observer<MovieInfoResponse>{
            setUpcomingMovieAdapter(it)
        })

        viewModel.liveTopRatedMovie.observe(this, Observer<MovieInfoResponse>{
            setTopRatedMovieAdapter(it)
        })
    }
}