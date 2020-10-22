package com.project.monopad.ui.view.home

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.FragmentHomeBinding
import com.project.monopad.model.network.response.MovieInfoResponse
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.adapter.MovieAdapter
import com.project.monopad.ui.adapter.MovieCase
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
        viewModel.livePopularMovie.observe(this, Observer<MovieInfoResponse>{
            popularAdapter.setMovies(it.results)
            viewDataBinding.homeViewpager.adapter = popularAdapter

        })

        viewModel.liveNowPlayingMovie.observe(this, Observer<MovieInfoResponse>{
            nowPlayingAdapter.setMovies(it.results)
            viewDataBinding.homeRvNowPlaying.adapter = nowPlayingAdapter
        })

        viewModel.liveUpcomingMovie.observe(this, Observer<MovieInfoResponse>{
            upcomingAdapter.setMovies(it.results)
            viewDataBinding.homeRvUpcoming.adapter = upcomingAdapter

        })

        viewModel.liveTopRatedMovie.observe(this, Observer<MovieInfoResponse>{
            topRatedAdapter.setMovies(it.results)
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