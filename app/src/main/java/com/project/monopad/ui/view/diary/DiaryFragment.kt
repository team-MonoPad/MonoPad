package com.project.monopad.ui.view.diary

import android.widget.Toast
import androidx.lifecycle.observe
import com.project.monopad.R
import com.project.monopad.databinding.FragmentDiaryBinding
import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import com.project.monopad.model.network.dto.Genre
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.viewmodel.DiaryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiaryFragment : BaseFragment<FragmentDiaryBinding, DiaryViewModel>() {

    override val viewModel: DiaryViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_diary

    override fun initStartView() {
        listenerSetting()
    }

    override fun initDataBinding() {
        viewModel.deleteAllReview()
        val sampleMovie = Movie(1225,
            "https://image.tmdb.org/t/p/w342/5lgyNwFqyaMMNW484rLgw7aRRZs.jpg"
            , "괴물",
            "overview",
            "2020/08/01",
            listOf(Genre(1,"action"), Genre(2,"fantasy"))
        )
        val sampleReview = Review(review_poster = "https://image.tmdb.org/t/p/w342/5lgyNwFqyaMMNW484rLgw7aRRZs.jpg"
            ,title = "괴물"
            ,date = "2020/10/16"
            ,comment = "good!! nice!!"
            , rating = 1.1
            , movie = sampleMovie
        )
        viewModel.insertReviewWithMovie(sampleReview)
        viewModel.getAllReview()
    }

    override fun initAfterBinding() {
        observeReviewData()
    }

    private fun observeReviewData() {
        viewModel.reviewData.observe(this) {
            viewDataBinding.calendarView.calendarAdapter.setReview(it)
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
                    Toast.makeText(context,"리뷰"+reviews.size+"개", Toast.LENGTH_SHORT).show()
                    val bottomFrag = DiaryListBottomSheetFragment()
                    bottomFrag.show(requireActivity().supportFragmentManager, "approval")
                }
                else {
                    Toast.makeText(context,"리뷰없음", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}