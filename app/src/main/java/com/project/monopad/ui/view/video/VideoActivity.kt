package com.project.monopad.ui.view.video

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.monopad.R
import com.project.monopad.databinding.ActivityVideoBinding

/**
 * 액티비티를 다이어로그처럼 만드는 방법
 * https://mainia.tistory.com/2027
 *
 * 유튜브 API 라이브러리
 * https://github.com/PRNDcompany/YouTubePlayerView
 */
class VideoActivity : AppCompatActivity() {

    val layoutResourceId: Int
        get() = R.layout.activity_video

    lateinit var viewDataBinding: ActivityVideoBinding

    private val id : String?
        get() = intent.getStringExtra("video_key")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
        viewDataBinding.activity = this
        this.setFinishOnTouchOutside(false)
        initStartView()
    }

    fun initStartView() {
        viewDataBinding.videoYoutubePlayerView.play(id!!)
    }
    fun finishBtnOnClick(){
        finish()
    }
}

