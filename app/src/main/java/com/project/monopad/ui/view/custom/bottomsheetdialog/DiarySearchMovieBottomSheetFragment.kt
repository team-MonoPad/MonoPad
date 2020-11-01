package com.project.monopad.ui.view.custom.bottomsheetdialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.monopad.R
import com.project.monopad.databinding.FragmentDiarySearchMovieBottomSheetBinding
import com.project.monopad.ui.adapter.SearchAdapter
import com.project.monopad.ui.view.detail.DetailActivity
import com.project.monopad.ui.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_diary_search_movie_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_diary_search_movie_bottom_sheet.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiarySearchMovieBottomSheetFragment : BottomSheetDialogFragment(){

    lateinit var searchViewBinding : View

    private val viewModel: SearchViewModel by viewModel()

    private val searchAdapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentDiarySearchMovieBottomSheetBinding
                = DataBindingUtil.inflate<FragmentDiarySearchMovieBottomSheetBinding>(
            inflater,
            R.layout.fragment_diary_search_movie_bottom_sheet,
            container,
            false
        ).root
        searchViewBinding = fragmentDiarySearchMovieBottomSheetBinding

        initStartView()
        initDataBinding()
        initSearchView()

        return searchViewBinding
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogs = BottomSheetDialog(requireContext(),theme)
        dialogs.setOnShowListener { setupFullHeight() }
        return  dialogs
    }

    private fun setupFullHeight() {
        val behavior = BottomSheetBehavior.from(bottom_sheet)
        val layoutParams = bottom_sheet.layoutParams
        var windowHeight : Int = 0
        windowHeight = if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){ getWindowHeightVerR() }else{ getWindowHeightOld() }
        if (layoutParams != null) { layoutParams.height = windowHeight }
        bottom_sheet.layoutParams = layoutParams
        behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isGestureInsetBottomIgnored
            isFitToContents = true
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState==4){ dismiss() }
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        setBottomSheetFullScreen()
    }

    private fun setBottomSheetFullScreen(){
        val dialog = dialog
        if (dialog != null) {
            val bottomSheet: View = dialog.findViewById(R.id.bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT //Can write to the height you want
        }
        val view = view
        requireView().post {
            val parent = view?.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view.measuredHeight
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getWindowHeightVerR(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.let {
            it.applicationContext.display!!.getRealMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }
        return displayMetrics.heightPixels
    }

    private fun getWindowHeightOld(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.let {
            it.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }
        return displayMetrics.heightPixels
    }

    fun initStartView() {
        setHasOptionsMenu(true)
        recyclerViewSetting()
    }

    private fun initDataBinding(){
        observeSearchMovieData()
    }

    private fun initSearchView(){
        searchViewBinding.sv_diary_movie_search.apply {
            isIconified = false
            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
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
    }

    private fun observeSearchMovieData(){
        viewModel.searchMovieData.observe(this) {
            rv_diary_search_movie.adapter = searchAdapter
            if (it.isNotEmpty()){
                searchViewBinding.iv_search_state.visibility = View.GONE
                searchViewBinding.tv_search_state.visibility = View.GONE
                searchAdapter.setList(it)
            }else{
                searchAdapter.refreshList()
                searchViewBinding.iv_search_state.apply {
                    setImageResource(R.drawable.ic_baseline_outlet_24)
                    visibility = View.VISIBLE
                }
                searchViewBinding.tv_search_state.apply {
                    text = getString(R.string.no_data_exception)
                    visibility = View.VISIBLE
                }
            }
        }
        searchAdapter.apply {
            setOnSearchClickListener {
                val intent = Intent(requireContext(), DetailActivity::class.java).putExtra("movie_id", it)
                startActivity(intent)
            }
            setOnSearchTouchListener {
                searchViewHide()
            }
        }
    }

    private fun searchViewHide(){
        searchViewBinding.sv_diary_movie_search.clearFocus()
        val im = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(searchViewBinding.sv_diary_movie_search.windowToken,0)
    }

    private fun recyclerViewSetting(){
        searchViewBinding.rv_diary_search_movie.apply {
            layoutManager = GridLayoutManager(context, 3).apply {
                orientation = GridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }
}