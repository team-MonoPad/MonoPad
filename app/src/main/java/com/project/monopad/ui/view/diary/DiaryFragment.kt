package com.project.monopad.ui.view.diary

import android.widget.Toast
import androidx.lifecycle.observe
import com.project.monopad.R
import com.project.monopad.databinding.FragmentDiaryBinding
import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import com.project.monopad.model.network.dto.Genre
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.view.custom.bottomsheetdialog.BottomSheetListAdapter
import com.project.monopad.ui.view.custom.bottomsheetdialog.DiaryListBottomSheetFragment
import com.project.monopad.ui.view.custom.bottomsheetdialog.DiarySearchMovieBottomSheetFragment
import com.project.monopad.ui.viewmodel.DiaryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class DiaryFragment : BaseFragment<FragmentDiaryBinding, DiaryViewModel>() {

    override val viewModel: DiaryViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_diary


    override fun initStartView() {
        listenerSetting()
    }

    override fun initDataBinding() {
        viewModel.deleteAllReview()

        val poster_path = "https://image.tmdb.org/t/p/w342/5lgyNwFqyaMMNW484rLgw7aRRZs.jpg"
        val title = "괴물"
        val nowDate = Date()

        viewModel.downloadImage(poster_path, title)

        viewModel.imagePathData.observe(this) {
            if (it.isNotBlank()){
                val sampleMovie = Movie(
                    id = 1225,
                    title = "괴물",
                    overview = "overview",
                    release_date = "2020/08/01",
                    genres = listOf(Genre(1,"action"), Genre(2,"fantasy"))
                )
                val sampleReview = Review(
                    id = 1225
                    , review_poster = it
                    , title = "괴물은 재밌다"
                    , date = nowDate
                    , comment = "good!! nice!!"
                    , rating = 1.1
                    , movie = sampleMovie
                )

                viewModel.insertReviewWithMovie(sampleReview)
            }
        }
        viewModel.getAllReview()
    }

    override fun initAfterBinding() {
        observeReviewData()
    }

    private fun observeReviewData() {
        viewModel.reviewData.observe(this) {
            viewDataBinding.calendarView.calendarAdapter.setList(it)
            viewDataBinding.calendarView.notifyCalendarChanged()
        }
    }

    private fun listenerSetting() {
        viewDataBinding.calendarView.setonDayClickListener{ month, item ->
            val itemYear = item.year
            val itemMonth = item.month
            val itemDay = item.day
            val reviews = item.reviews

            if(itemMonth!=month){
                viewDataBinding.calendarView.onCalendarUpdated(itemYear, itemMonth, 1)
            }
            else {
                if(!reviews.isNullOrEmpty()){
                    Toast.makeText(context,"리뷰 "+reviews.size+"개", Toast.LENGTH_SHORT).show()
                    showBottomListDialog(reviews)
                }
                else {
                    showBottomSearchDialog()
                }
            }
        }
    }

    private fun showBottomListDialog(reviews: List<Review>) {
        val bottomSheetListAdapter = BottomSheetListAdapter()
        val bottomSheetFragment = DiaryListBottomSheetFragment(bottomSheetListAdapter)

        bottomSheetListAdapter.setList(reviews)
        bottomSheetListAdapter.setOnReviewClickListener {
            Toast.makeText(context, "review id: $it", Toast.LENGTH_SHORT).show()
            bottomSheetFragment.dismiss()
        }

        bottomSheetFragment.show(requireActivity().supportFragmentManager, "approval")
    }

    private fun showBottomSearchDialog() {
        val bottomSheetSearchFragment = DiarySearchMovieBottomSheetFragment()
        bottomSheetSearchFragment.show(requireActivity().supportFragmentManager, "approval")
    }

}