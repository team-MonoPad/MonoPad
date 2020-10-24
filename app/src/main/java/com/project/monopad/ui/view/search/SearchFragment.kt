package com.project.monopad.ui.view.search

import android.view.Menu
import android.view.MenuInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.FragmentSearchBinding
import com.project.monopad.ui.adapter.SearchAdapter
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    override val viewModel: SearchViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_search

    override fun initStartView() {
        setHasOptionsMenu(true)
        recyclerViewSetting()
    }

    override fun initDataBinding() {
        viewModel.getSearchData()
    }

    override fun initAfterBinding() {
        observeSearchMovieData()
    }

    private fun observeSearchMovieData(){
        val searchAdapter = SearchAdapter()
        viewModel.searchMovieData.observe(this, {
            rv_search_movie.adapter = searchAdapter
            searchAdapter.setList(it)
        })
    }

    /* view setting */
    private fun recyclerViewSetting(){
        rv_search_movie.apply {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }
}

