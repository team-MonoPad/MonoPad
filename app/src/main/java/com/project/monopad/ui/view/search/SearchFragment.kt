package com.project.monopad.ui.view.search

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.FragmentSearchBinding
import com.project.monopad.ui.adapter.SearchAdapter
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.view.detail.DetailActivity
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
        viewModel.getSearchData("")
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
        searchAdapter.setOnSearchClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java).putExtra("movie_id", it)
            startActivity(intent)
        }
    }

    /* view setting */
    private fun recyclerViewSetting(){
        rv_search_movie.apply {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }
    }

    /* menu setting */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val mainSearchView = menu.findItem(R.id.action_search)
        val sv = mainSearchView.actionView as SearchView

        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String): Boolean {
                viewModel.getSearchData(p0)
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                viewModel.getSearchData(p0)
                return true
            }

        })
    }


}

