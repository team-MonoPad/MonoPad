package com.project.monopad.ui.view.diary

import android.content.Intent
import androidx.lifecycle.observe
import com.project.monopad.R
import com.project.monopad.databinding.FragmentDiaryBinding
import com.project.monopad.data.model.entity.Movie
import com.project.monopad.data.model.entity.Review
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.adapter.DiaryListBottomSheetAdapter
import com.project.monopad.ui.view.custom.bottomsheetdialog.DiaryListBottomSheetFragment
import com.project.monopad.ui.view.custom.bottomsheetdialog.DiarySearchMovieBottomSheetFragment
import com.project.monopad.ui.view.edit.EditActivity
import com.project.monopad.ui.viewmodel.DiaryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiaryFragment : BaseFragment<FragmentDiaryBinding, DiaryViewModel>() {

    override val viewModel: DiaryViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_diary


    override fun initStartView() {
        initClickEvent()
    }

    override fun initDataBinding() {
        //viewModel.deleteAllReview()

//        val poster_path = "https://image.tmdb.org/t/p/w342/5lgyNwFqyaMMNW484rLgw7aRRZs.jpg"
//        val title = "괴물"
//        val nowDate = Date()
//        viewModel.downloadImage(poster_path, title)
//
//        viewModel.imagePathData.observe(this) {
//            if (it.isNotBlank()){
//                val sampleMovie = Movie(
//                    id = 1225,
//                    title = "괴물",
//                    overview = "overview",
//                    release_date = "2020/08/01",
//                    genres = listOf(Genre(1,"action"), Genre(2,"fantasy"))
//                )
//                val sampleReview = Review(
//                    id = 1225
//                    , review_poster = it
//                    , title = "괴물은 재밌다"
//                    , date = nowDate
//                    , comment = "good!! nice!!"
//                    , rating = 1.1
//                    , movie = sampleMovie
//                )
//
//                viewModel.insertReviewWithMovie(sampleReview)
//            }
//        }

        viewModel.getAllReview()
    }

    override fun initAfterBinding() {
        observeReviewData()
    }

    private fun observeReviewData() {
        viewModel.reviewData.observe(this) {
            viewDataBinding.calendarView.notifyDataChanged(it)
        }
    }

    private fun initClickEvent() {
        viewDataBinding.calendarView.setonDayClickListener { item ->
            val reviews = item.reviews
            if(reviews.isNotEmpty()){
                showBottomListDialog(reviews)
            }
            else {
                showBottomSearchDialog()
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

        bottomSheetDiaryListFragment.show(requireActivity().supportFragmentManager, "approval")
    }

    private fun showBottomSearchDialog() {
        val bottomSheetSearchFragment = DiarySearchMovieBottomSheetFragment()
        bottomSheetSearchFragment.show(requireActivity().supportFragmentManager, "approval")
    }

    private fun startEditActivity(movie: Movie) {
        val intent = Intent(requireContext(), EditActivity::class.java).apply {
            putExtra("movie_data", movie)
            putExtra("isFirst",false)
            putExtra("isReselect",false)
        }
        startActivity(intent)
    }

}