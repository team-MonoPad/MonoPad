package com.project.monopad.ui.view.home

import android.content.Intent
import android.util.Log.d
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.project.monopad.R
import com.project.monopad.databinding.FragmentHomeBinding
import com.project.monopad.model.network.response.MovieInfoResultResponse
import com.project.monopad.ui.adapter.home.MovieAdapter
import com.project.monopad.ui.adapter.home.MovieCase
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.view.video.VideoActivity
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

    private var mPopularListSize = 0
    private val mIndicatorCount = 3

    override fun initStartView() {
        //Set LayoutManager
        viewDataBinding.homeRvNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.homeRvUpcoming.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.homeRvTopRated.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        //set Indicator
        viewDataBinding.homeIndicator.createIndicators(mIndicatorCount,0)
        viewDataBinding.homeIndicator.setViewPager(viewDataBinding.homeViewpager)

        popularAdapter.registerAdapterDataObserver(viewDataBinding.homeIndicator.adapterDataObserver)
        // 뷰페이저 리스너 (ViewPager 1과 다르게 2는 필요한 것만 오버라이딩이 가능하다.
        viewDataBinding.homeViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            //부드러운 스크롤 또는 사용자가 시작한 터치 스크롤의 일부로 현재 페이지가 스크롤 될 때 호출됩니다.
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    viewDataBinding.homeViewpager.currentItem = position;
                }
            }

            // 화면 전환이 끝났을 때 해당 포지션을 반환
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                d("TEST onPageSelected", position.toString())
                when (position) {
                    0 -> viewDataBinding.homeIndicator.animatePageSelected(0) //첫 번째 페이지 -> 0
                    mPopularListSize-1 -> viewDataBinding.homeIndicator.animatePageSelected(2)//마지막 페이지 -> 2
                    else -> viewDataBinding.homeIndicator.animatePageSelected(1) // 이외의 페이지 -> 1
                }

            }
        })


        //Set ClickListener
        popularAdapter.setOnTrailerClickListener {
            viewModel.popularMovieVideoData(it)
        }

        popularAdapter.setOnItemClickListener {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
//            startActivity(Intent(activity, DetailActivity::class.java).putExtra("id", it))
        }

        nowPlayingAdapter.setOnItemClickListener {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

//            startActivity(Intent(activity, DetailActivity::class.java).putExtra("id", it))
        }

        topRatedAdapter.setOnItemClickListener {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(requireView()).navigate(R.id.action_home_to_detail)
//            startActivity(Intent(activity, DetailActivity::class.java).putExtra("id", it))

        }

        upcomingAdapter.setOnItemClickListener {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
//            startActivity(Intent(activity, DetailActivity::class.java).putExtra("id", it))
        }
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
            mPopularListSize = it.size
        })

        viewModel.nowPlayingMovieData.observe(this, Observer<List<MovieInfoResultResponse>>{
            nowPlayingAdapter.setMovies(it)
            viewDataBinding.homeRvNowPlaying.adapter = nowPlayingAdapter
        })

        viewModel.upcomingMovieData.observe(this, Observer<List<MovieInfoResultResponse>>{
            upcomingAdapter.setMovies(it)
            viewDataBinding.homeRvUpcoming.adapter = upcomingAdapter
        })

        viewModel.topRatedMovieData.observe(this, Observer<List<MovieInfoResultResponse>>{
            topRatedAdapter.setMovies(it)
            viewDataBinding.homeRvTopRated.adapter = topRatedAdapter
        })

        viewModel.popularMovieVideoData.observe(this, {
            startActivity(Intent(activity, VideoActivity::class.java).putExtra("key", it[0].key))
        })
    }
}