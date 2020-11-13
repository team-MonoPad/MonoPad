package com.project.monopad.ui.view.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.project.monopad.R
import com.project.monopad.databinding.FragmentHomeBinding
import com.project.monopad.extension.intentActionWithBundle
import com.project.monopad.extension.intentActionWithBundleSingleTop
import com.project.monopad.ui.adapter.MovieAdapter
import com.project.monopad.util.state.MovieCase
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.view.detail.DetailActivity
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
    private val mIndicatorCount = 5

    override fun initStartView() {
        progressDialog.show()
        val layout = { context: Context -> LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        ) }

        viewDataBinding.homeRvNowPlaying.apply {
            layoutManager = layout(context)
            setHasFixedSize(true) //setHasFixedSize 를 설정하지 않으면 항목의 크기가 변경되어 비용이 많이 드는 작업을 하는지 확인한다.
        }
        viewDataBinding.homeRvUpcoming.apply {
            layoutManager = layout(context)
            setHasFixedSize(true)
        }
        viewDataBinding.homeRvTopRated.apply {
            layoutManager = layout(context)
            setHasFixedSize(true)
        }

        viewDataBinding.homeRvNowPlaying.adapter = nowPlayingAdapter
        viewDataBinding.homeRvUpcoming.adapter = upcomingAdapter
        viewDataBinding.homeRvTopRated.adapter = topRatedAdapter

        //set Indicator
        viewDataBinding.homeIndicator.apply {
            createIndicators(mIndicatorCount, 0)
            setViewPager(viewDataBinding.homeViewpager)
        }
        viewDataBinding.homeViewpager.adapter = popularAdapter

//        popularAdapter.registerAdapterDataObserver(viewDataBinding.homeIndicator.adapterDataObserver) //어댑터의 데이터 변화를 구독한다.
        viewDataBinding.homeViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            // 화면 전환이 끝났을 때 해당 포지션을 반환. 페이지의 변화가 생겼을때 호출되는 메서드이다.
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> viewDataBinding.homeIndicator.animatePageSelected(0) //첫 번째 페이지
                    1 -> viewDataBinding.homeIndicator.animatePageSelected(1) //첫 번째 페이지
                    mPopularListSize - 2 -> viewDataBinding.homeIndicator.animatePageSelected(3)//뒤에서 두번째 페이지
                    mPopularListSize - 1 -> viewDataBinding.homeIndicator.animatePageSelected(4)//마지막 페이지
                    else -> viewDataBinding.homeIndicator.animatePageSelected(2) // 이외의 페이지 -> 가운데 점
                }

            }
        })

        popularAdapter.setOnTrailerClickListener {
            viewModel.popularVideoData(it)
        }

        popularAdapter.setOnItemClickListener {
            requireContext().intentActionWithBundleSingleTop(DetailActivity::class){putInt("movie_id", it) }
        }

        nowPlayingAdapter.setOnItemClickListener {
            requireContext().intentActionWithBundleSingleTop(DetailActivity::class){putInt("movie_id", it)}
        }

        topRatedAdapter.setOnItemClickListener {
            requireContext().intentActionWithBundleSingleTop(DetailActivity::class){putInt("movie_id", it)}
        }

        upcomingAdapter.setOnItemClickListener {
            requireContext().intentActionWithBundleSingleTop(DetailActivity::class){putInt("movie_id", it)}
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
        viewModel.popularMovieData.observe(viewLifecycleOwner, {
            popularAdapter.setMovies(it)
            mPopularListSize = it.size
            popularAdapter.notifyDataSetChanged()
        })

        viewModel.nowPlayingMovieData.observe(viewLifecycleOwner, {
            nowPlayingAdapter.setMovies(it)
            nowPlayingAdapter.notifyDataSetChanged()
        })

        viewModel.upcomingMovieData.observe(viewLifecycleOwner, {
            upcomingAdapter.setMovies(it)
            upcomingAdapter.notifyDataSetChanged()
        })

        viewModel.topRatedMovieData.observe(viewLifecycleOwner, {
            topRatedAdapter.setMovies(it)
            topRatedAdapter.notifyDataSetChanged()
        })
          
        viewModel.videoData.observe(this, {
            requireContext().intentActionWithBundle(VideoActivity::class) {
                putString(
                    "video_key",
                    it[0].key
                )
            }
        })

        viewModel.count.observe(this, {
            if (it == 4) { progressDialog.dismiss() }
        })
    }

}