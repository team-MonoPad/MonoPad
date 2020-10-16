package com.project.monopad.ui.view.vedio

import android.util.Log.d
import android.view.MenuItem
import com.project.monopad.R
import com.project.monopad.databinding.ActivityVideoBinding
import com.project.monopad.model.network.response.MovieVideoResultResponse
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoActivity : BaseActivity<ActivityVideoBinding, VideoViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_video

    override val viewModel: VideoViewModel by viewModel()

    private var movieId = 337401 //tmdb movie id
    private lateinit var videoList: ArrayList<MovieVideoResultResponse>

    override fun initStartView() {
        setSupportActionBar(viewDataBinding.videoToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //back button
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.title = "영화제목"
        if (intent.hasExtra("movie")) {

//            movieId = intent.getIntExtra("id", 0)
//            viewDataBinding.videoTvTitle.text =  intent.getStringExtra("title")
//            viewDataBinding.videoTvOverview.text = intent.getStringExtra("overview")
//            viewDataBinding.videoTvReleaseDate.text = intent.getStringExtra("release")
        }
    }

    override fun initBeforeBinding() {
        // get data
        viewModel.getVideoData(id = movieId)
    }

    override fun initAfterBinding() {
        //observing & add item to adapter
        viewModel.liveVideo.observe(this, {
            videoList = it.results as ArrayList<MovieVideoResultResponse>
            viewDataBinding.videoYoutubePlayerView.play(videoList[0].key) //key is youtube id
            viewDataBinding.videoTvTitle.text = videoList[0].name //영상 제목
            d("key", videoList[0].key)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { //onClick toolbar back button
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}