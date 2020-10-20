package com.project.monopad.ui.view.detail

import android.content.Intent
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.ActivityPersonDetailBinding
import com.project.monopad.ui.adapter.PersonFilmographyAdapter
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.PersonViewModel
import com.project.monopad.util.DetailParsingUtil
import kotlinx.android.synthetic.main.activity_person_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonDetailActivity : BaseActivity<ActivityPersonDetailBinding, PersonViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_person_detail

    override val viewModel: PersonViewModel by viewModel()

    private val id : Int
        get() = intent.getIntExtra("person_id", 0)

    private val personFilmographyAdapter = PersonFilmographyAdapter()

    override fun initStartView() {
        recyclerViewSet()
        onClickEvent()
    }

    override fun initBeforeBinding() {
        viewModel.getPersonDetail(id)
        viewModel.getPersonDetailCredits(id)
    }

    override fun initAfterBinding() {
        viewModel.personDetailInfo.observe(this) {
            viewDataBinding.model = it
            tv_person_detail_known_name.text = DetailParsingUtil.koreaNameParsing(it.also_known_as)
        }

        viewModel.personDetailMovie.observe(this) { it ->
            rv_person_detail.adapter = personFilmographyAdapter
            personFilmographyAdapter.setFilmoList(it.sortedWith( compareBy { it.release_date }).reversed())
        }
    }

    private fun recyclerViewSet(){
        rv_person_detail.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun onClickEvent(){
        personFilmographyAdapter.setOnFilmoMovieClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("movie_id", it)
            startActivity(intent)
            finishAndRemoveTask()
        }
        iv_back.setOnClickListener {
            onBackPressed()
        }
    }
}