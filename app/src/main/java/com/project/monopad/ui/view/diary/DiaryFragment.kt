package com.project.monopad.ui.view.diary

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.observe
import com.project.monopad.R
import com.project.monopad.databinding.FragmentDiaryBinding
import com.project.monopad.data.model.entity.Movie
import com.project.monopad.data.model.entity.Review
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.adapter.DiaryListBottomSheetAdapter
import com.project.monopad.ui.view.custom.bottomsheetdialog.DiaryListBottomSheetFragment
import com.project.monopad.ui.view.custom.bottomsheetdialog.DiarySearchMovieBottomSheetFragment
import com.project.monopad.ui.view.custom.dialog.CheckDialog
import com.project.monopad.ui.view.edit.EditActivity
import com.project.monopad.ui.viewmodel.DiaryViewModel
import com.project.monopad.util.MainUtil.convertCalendarToString
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class DiaryFragment : BaseFragment<FragmentDiaryBinding, DiaryViewModel>() {

    override val viewModel: DiaryViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_diary

    override fun initStartView() {
        setHasOptionsMenu(true)
        initClickEvent()
        progressDialog.show()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        observeReviewData()
    }

    private fun observeReviewData() {
        viewModel.reviewData.observe(viewLifecycleOwner){
            viewDataBinding.calendarView.notifyDataChanged(it)
            progressDialog.dismiss()
        }
    }

    private fun initClickEvent() {
        viewDataBinding.calendarView.setonDayClickListener {
            if(it.reviews.isNotEmpty()){
                showBottomListDialog(it.reviews)
            }
            else {
                showBottomSearchDialog(it.calendar)
            }
        }
    }

    private fun showBottomListDialog(reviews: List<Review>) {
        val bottomSheetListAdapter = DiaryListBottomSheetAdapter()
        val bottomSheetDiaryListFragment = DiaryListBottomSheetFragment(bottomSheetListAdapter)

        bottomSheetListAdapter.setList(reviews)
        bottomSheetListAdapter.setOnReviewClickListener {
            startEditActivity(it)
            bottomSheetDiaryListFragment.dismiss()
        }
        bottomSheetDiaryListFragment.show(childFragmentManager, "approval")
    }

    private fun showBottomSearchDialog(calendar: Calendar) {
        val bottomSheetSearchFragment = DiarySearchMovieBottomSheetFragment()
        bottomSheetSearchFragment.arguments = Bundle().also {
            it.putString("date",convertCalendarToString(calendar,"yyyy년 MM월 dd일"))
        }
        bottomSheetSearchFragment.show(childFragmentManager, "approval")
    }

    private fun startEditActivity(movie: Movie) {
        val intent = Intent(requireContext(), EditActivity::class.java).apply {
            putExtra("movie_data", movie)
            putExtra("isFirst",false)
            putExtra("isReselect",false)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_diary, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete_all -> deleteAllReview()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllReview() {
        CheckDialog(requireContext()).run {
            setAcceptBtnOnClickListener{
                viewModel.deleteAllReview()
            }
            start(getString(R.string.message_delete_all_review))
        }
    }
}