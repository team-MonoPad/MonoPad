package com.project.monopad.ui.view.detail

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.ActivityDetailBinding
import com.project.monopad.ui.adapter.CasterAdapter
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_detail

    override val viewModel: DetailViewModel by viewModel()

    private lateinit var casterAdapter: CasterAdapter

    /* start activity */
    override fun initStartView() {
        toolbarLayoutSetting()
        casterViewSetting()
    }

    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
        viewModel.getDetailData()
    }

    override fun initAfterBinding() {
        observeMovieDetailData()
        observeMovieCrewData()
        observeMovieCasterData()
    }

    /* observe */
    private fun observeMovieDetailData(){
        viewModel.movieDetailData.observe(this, {
            toolbar_layout.title = it.title
            tv_detail_release_date.text = viewModel.releaseDateParsing(it.release_date)
            tv_detail_runtime.text = viewModel.runtimeParsing(it.runtime)
            tv_detail_genre.text = viewModel.genreParsing(it.genres)
            tv_detail_overview.text = it.overview
        })

    }

    private fun observeMovieCrewData(){
        viewModel.movieCrewData.observe(this, {
            tv_detail_director.text = viewModel.directorParsing(it)
        })
    }

    private fun observeMovieCasterData(){
        casterAdapter = CasterAdapter()
        viewModel.movieCastData.observe(this, {
            rv_detail_caster.adapter = casterAdapter
            casterAdapter.setList(viewModel.casterParsing(it))
        })
    }

    /* view setting */
    private fun casterViewSetting(){
        rv_detail_caster.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    private fun toolbarLayoutSetting(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_layout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar)
    }

    /* toolbar menu setting */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_edit -> {
                // 다이어리 작성
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
        return true
    }


}