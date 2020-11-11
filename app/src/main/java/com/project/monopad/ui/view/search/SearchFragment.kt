package com.project.monopad.ui.view.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.FragmentSearchBinding
import com.project.monopad.ui.adapter.SearchAdapter
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.view.detail.DetailActivity
import com.project.monopad.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    override val viewModel: SearchViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_search

    lateinit var searchView : SearchView

    override fun initStartView() {
        setHasOptionsMenu(true)
        recyclerViewSetting()
    }

    override fun initDataBinding() {
        //
    }

    override fun initAfterBinding() {
        observeSearchMovieData()
    }

    private fun observeSearchMovieData(){
        val searchAdapter = SearchAdapter()
        viewModel.searchMovieData.observe(viewLifecycleOwner, Observer{
           viewDataBinding.rvSearchMovie.adapter = searchAdapter
            searchAdapter.setList(it)
        })
        searchAdapter.setOnSearchClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java).putExtra("movie_id", it)
            startActivity(intent)
        }
        searchAdapter.setOnSearchTouchListener {
            searchViewHide()
        }

    }

    /* recyclerView setting */
    @SuppressLint("ClickableViewAccessibility")
    private fun recyclerViewSetting(){
        viewDataBinding.rvSearchMovie.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(true)
        }
        viewDataBinding.rvSearchMovie.setOnTouchListener{ _, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    searchViewHide()
                    false
                }
                else -> false
            }
        }
    }

    /* searchView setting */
    private fun searchViewSetting(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getSearchData(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.getSearchData(query)
                return true
            }

        })
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun searchViewHide(){
        searchView.clearFocus()
        val im = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(searchView.windowToken, 0)
    }

    /* menu setting */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchViewSetting()
    }



}

