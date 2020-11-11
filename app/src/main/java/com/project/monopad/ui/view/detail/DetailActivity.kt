package com.project.monopad.ui.view.detail

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.ActivityDetailBinding
import com.project.monopad.extension.intentActionWithBundle
import com.project.monopad.extension.showToast
import com.project.monopad.data.model.entity.Movie
import com.project.monopad.extension.intentActionWithBundleSingleTop
import com.project.monopad.ui.adapter.CasterAdapter
import com.project.monopad.ui.adapter.OtherMovieAdapter
import com.project.monopad.ui.adapter.TrailerAdapter
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.view.edit.EditActivity
import com.project.monopad.ui.view.select.ImageSelectActivity
import com.project.monopad.ui.view.video.VideoActivity
import com.project.monopad.ui.viewmodel.DetailViewModel
import com.project.monopad.util.DetailParsingUtil
import com.project.monopad.util.OtherMovieCase
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_detail

    override val viewModel: DetailViewModel by viewModel()

    private lateinit var menuReview: MenuItem
    private var reviewCheck: Boolean = false

    private val MOVIE_ID : Int
        get() = intent.getIntExtra("movie_id", 0)

    lateinit var intentMovieData : Movie

    /* start activity */
    override fun initStartView() {
        toolbarLayoutSetting()
        recyclerViewSetting()
    }

    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
        viewModel.getDetailData(MOVIE_ID)
        viewModel.getReviewData()
    }

    override fun initAfterBinding() {
        observeMovieDetailData()
        observeMovieCrewData()
        observeMovieCasterData()
        observeSimilarMovieData()
        observeRecommendMovieData()
        observeTrailerMovieData()
        observeReviewData()
    }

    /* observe */
    private fun observeMovieDetailData(){
        viewModel.movieDetailData.observe(this, {
            viewDataBinding.toolbarLayout.title = it.title
            viewDataBinding.contentLayout.apply {
                tvDetailReleaseDate.text = DetailParsingUtil.releaseDateParsing(it.release_date)
                tvDetailRuntime.text = DetailParsingUtil.runtimeParsing(it.runtime)
                tvDetailGenre.text = DetailParsingUtil.genreParsing(it.genres)
                tvDetailOverview.text = it.overview
            }
            intentMovieData = Movie(it.id,it.title,it.overview,it.release_date,it.genres)
        })
    }

    private fun observeMovieCrewData(){
        viewModel.movieCrewData.observe(this, {
            viewDataBinding.contentLayout.tvDetailDirector.text = DetailParsingUtil.directorParsing(it)
        })
    }

    private fun observeMovieCasterData(){
        val casterAdapter = CasterAdapter()
        viewModel.movieCastData.observe(this, {
            viewDataBinding.contentLayout.rvDetailCaster.adapter = casterAdapter
            casterAdapter.setList(DetailParsingUtil.casterParsing(it))
        })
        casterAdapter.setOnCasterClickListener {
            intentActionWithBundleSingleTop(PersonDetailActivity::class){putInt("person_id",it)}
        }
    }

    private fun observeSimilarMovieData(){
        val similarMovieAdapter = OtherMovieAdapter(OtherMovieCase.SIMILAR)
        viewModel.similarMovieData.observe(this, {
            viewDataBinding.contentLayout.run {
                rvDetailSimilarMovie.adapter = similarMovieAdapter
                similarMovieAdapter.setList(it)
                visibilitySetting(it.size, tvDetailSimilar, dvSimilarTop)
            }
        })
        similarMovieAdapter.setOnOtherClickListener {
            intentActionWithBundle(DetailActivity::class){putInt("movie_id",it)}
        }
    }

    private fun observeRecommendMovieData(){
        val recommendMovieAdapter = OtherMovieAdapter(OtherMovieCase.RECOMMEND)
        viewModel.recommendMovieData.observe(this, {
            viewDataBinding.contentLayout.run {
                rvDetailRecommendMovie.adapter = recommendMovieAdapter
                recommendMovieAdapter.setList(it)
                visibilitySetting(it.size, tvDetailRecommend, dvRecommendTop)
            }
        })
        recommendMovieAdapter.setOnOtherClickListener {
            intentActionWithBundle(DetailActivity::class){putInt("movie_id",it)}
        }
    }

    private fun observeTrailerMovieData(){
        val trailerAdapter = TrailerAdapter()
        viewModel.movieTrailerData.observe(this, {
            viewDataBinding.contentLayout.run {
                rvDetailTrailer.adapter = trailerAdapter
                trailerAdapter.setList(it)
                visibilitySetting(it.size, tvDetailTrailer, dvTrailerTop)
            }
        })
        trailerAdapter.setOnTrailerClickListener {
            intentActionWithBundleSingleTop(VideoActivity::class){putString("video_key",it)}
        }
    }

    private fun observeReviewData(){
        viewModel.reviewData.observe(this, {
            menuReview.setIcon(R.drawable.ic_baseline_edit_24)
            for (i in it.indices) {
                if (MOVIE_ID == it[i].id) {
                    menuReview.setIcon(R.drawable.ic_baseline_article_24)
                    reviewCheck = true
                    break
                }
            }
        })
    }

    /* view setting */
    private fun recyclerViewSetting(){
        val layout = { context: Context -> LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)}
        viewDataBinding.contentLayout.apply {
            rvDetailCaster.apply {
                layoutManager = layout(context)
                setHasFixedSize(true)
            }
            rvDetailSimilarMovie.apply{
                layoutManager = layout(context)
                setHasFixedSize(true)
            }
            rvDetailRecommendMovie.apply{
                layoutManager =layout(context)
                setHasFixedSize(true)
            }
            rvDetailTrailer.apply {
                layoutManager = layout(context)
                setHasFixedSize(true)
            }
        }
    }

    private fun toolbarLayoutSetting(){
        setSupportActionBar(viewDataBinding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewDataBinding.toolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar)

    }

    private fun visibilitySetting(size: Int, vararg v: View){
        if(size == 0){
            v[0].visibility = View.GONE
            v[1].visibility = View.GONE
        } else {
            v[0].visibility = View.VISIBLE
            v[1].visibility = View.VISIBLE
        }
    }

    /* toolbar menu setting */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_review -> {
                if (!reviewCheck) {
                    intentActionWithBundle(ImageSelectActivity::class){
                        putBoolean("isReselect",false)
                        putParcelable("movie_data",intentMovieData)
                    }
                } else {
                    intentActionWithBundle(EditActivity::class){
                        putParcelable("movie_data",intentMovieData)
                        putBoolean("isReselect",false)
                        putBoolean("isFirst",false)
                    }
                }
                true
            }
            R.id.action_share -> {
                showToast(this,"서비스 준비중입니다.")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_detail, menu)
        menuReview = menu.findItem(R.id.action_review)

        return true
    }

}