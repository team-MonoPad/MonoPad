package com.project.monopad.ui.view.diary

import android.util.Log
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
import java.text.SimpleDateFormat
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
        val testDate = "2020-10-24"
        val f = SimpleDateFormat("yyyy-MM-dd")
        val parseTestDate: Date = f.parse(testDate)

        viewModel.downloadImage(poster_path, title)

        viewModel.imagePathData.observe(this) {
            Log.d("Diary Fragment 1 ", it)
            if (it.isNotBlank()){
                Log.d("Diary Fragment 2 ", it)
                val sampleMovie = Movie(
                    id = 1225,
                    poster = it,
                    title = "괴물",
                    overview = "overview",
                    release_date = "2020/08/01",
                    genres = listOf(Genre(1,"action"), Genre(2,"fantasy"))
                )
                val sampleReview = Review(
                    review_poster = it
                    , title = "괴물"
                    , date = parseTestDate
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