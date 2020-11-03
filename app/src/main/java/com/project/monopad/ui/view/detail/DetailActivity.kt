package com.project.monopad.ui.view.detail

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.ActivityDetailBinding
import com.project.monopad.ui.adapter.CasterAdapter
import com.project.monopad.ui.adapter.OtherMovieAdapter
import com.project.monopad.ui.adapter.TrailerAdapter
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.view.review.ImageSelectActivity
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

    /* start activity */
    override fun initStartView() {
        toolbarLayoutSetting()
        recyclerViewSetting()
    }

    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
        viewModel.getDetailData(intent?.getIntExtra("movie_id", 89501) ?: 89501)
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
            val intent = Intent(this, PersonDetailActivity::class.java)
            intent.putExtra("person_id", it)
            startActivity(intent)
        }
    }

    private fun observeSimilarMovieData(){
        val similarMovieAdapter = OtherMovieAdapter(OtherMovieCase.SIMILAR)
        viewModel.similarMovieData.observe(this, {
            viewDataBinding.contentLayout.rvDetailSimilarMovie.adapter = similarMovieAdapter
            similarMovieAdapter.setList(it)
        })
        similarMovieAdapter.setOnOtherClickListener {
            val intent = Intent(this, DetailActivity::class.java).putExtra("movie_id", it)
            startActivity(intent)
            finish()
        }
    }

    private fun observeRecommendMovieData(){
        val recommendMovieAdapter = OtherMovieAdapter(OtherMovieCase.RECOMMEND)
        viewModel.recommendMovieData.observe(this, {
            viewDataBinding.contentLayout.rvDetailRecommendMovie.adapter = recommendMovieAdapter
            recommendMovieAdapter.setList(it)
        })
        recommendMovieAdapter.setOnOtherClickListener {
            val intent = Intent(this, DetailActivity::class.java).putExtra("movie_id", it)
            startActivity(intent)
            finish()
        }
    }

    private fun observeTrailerMovieData(){
        val trailerAdapter = TrailerAdapter()
        viewModel.movieTrailerData.observe(this, {
            viewDataBinding.contentLayout.rvDetailTrailer.adapter = trailerAdapter
            trailerAdapter.setList(it)
        })
        trailerAdapter.setOnTrailerClickListener {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            /* key 넘겨주기
            val intent = Intent(this, CLASS).putExtra("key", it)
            startActivity(intent)
             */
        }
    }

    private fun observeReviewData(){
        viewModel.reviewData.observe(this, {
            val movieId = intent?.getIntExtra("movie_id", 89501) ?: 89501
            menuReview.setIcon(R.drawable.ic_baseline_edit_24)
            for (i in it.indices) {
                if (movieId == it[i].id) {
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

    /* toolbar menu setting */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_review -> {
                if (!reviewCheck) {
                    Intent(this, ImageSelectActivity::class.java)
                        .putExtra("movie_id", intent?.getIntExtra("movie_id", 89501) ?: 89501)
                        .also {
                            startActivity(it)
                        }
                } else {
                    // go review edit view
                }
                true
            }
            R.id.action_share -> {
                // 공유하기
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