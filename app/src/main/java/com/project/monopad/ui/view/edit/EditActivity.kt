package com.project.monopad.ui.view.edit


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.monopad.R
import com.project.monopad.databinding.ActivityEditBinding
import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import com.project.monopad.model.network.dto.Genre
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.DiaryViewModel
import com.project.monopad.util.BaseUtil
import com.project.monopad.util.BaseUtil.IMAGE_URL
import com.project.monopad.util.DateUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class EditActivity : BaseActivity<ActivityEditBinding, DiaryViewModel>() {
;
    override val layoutResourceId: Int
        get() = R.layout.activity_edit

    override val viewModel: DiaryViewModel by viewModel()

    private var isFirst = false
    private var movie: Movie? = null
    private var imagePath: String? = "aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg"
    private lateinit var imm: InputMethodManager
    private lateinit var frontCard : AnimatorSet
    private lateinit var backCard : AnimatorSet
    private var isFront = false

    override fun initStartView() {
        viewDataBinding.activity = this;
        //Set Toolbar
        setSupportActionBar(viewDataBinding.editToolbar) //툴바를 액션바로 등록
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //back button
        supportActionBar!!.setDisplayShowCustomEnabled(true)

//        intent.let{
//            isFirst = intent.getBooleanExtra("isFirst", false)
//        movie = intent.getParcelableExtra<Movie>("movie")
//            if (isFirst){ //첫 작성이라면,
//                setFirstReview()
//            }else{ //첫 작성이 아니라면
//                viewModel.getReviewByReviewId(id = movie.id)
//            }
//        }

        movie = Movie(
            id = 1225,
            title = "괴물",
            overview = "overview",
            release_date = "2020/08/01",
            genres = listOf(Genre(1,"action"), Genre(2,"fantasy"))
        )
        if (isFirst) setFirstReview() else viewModel.getReviewByReviewId(id = movie!!.id)

        //Flip CardView Setting
        val scale : Float = applicationContext.resources.displayMetrics.density
        cv_edit_diary_edit.cameraDistance = 8000 * scale
        cv_edit_diary_poster.cameraDistance = 8000 * scale
        frontCard = AnimatorInflater.loadAnimator(applicationContext,R.animator.front_animator) as AnimatorSet
        backCard = AnimatorInflater.loadAnimator(applicationContext,R.animator.back_animator) as AnimatorSet
    }

    override fun initBeforeBinding() {

    }

    //observing & add item to adapter\
    override fun initAfterBinding() {
        //리뷰 저장/수정 응답 결과
        viewModel.isCompleted.observe(this){
            isFirst = !it // Review insert/update 완료 시 isFirst 값 false 로 변경
            Toast.makeText(this@EditActivity, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        }

        //이미 저장한 리뷰가 있다면, 받아온 데이터로 View 세팅
        viewModel.singleReviewData.observe(this){
            viewDataBinding.review = it
            viewDataBinding.movie = it.movie
            imagePath = it.review_poster
            viewDataBinding.editTvDate.text = DateUtil.convertDateToString(it.date)

            Glide.with(this@EditActivity)
                .load(it.review_poster)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(BlurTransformation(13, 1)))
                .into(viewDataBinding.editIvBlurBackground)
        }
    }

    private fun setFirstReview(){
//        movie = intent.getParcelableExtra<Movie>("movie")!!
//        imagePath = intent.getStringExtra("image")
        viewDataBinding.movie = movie
        viewDataBinding.editTvDate.text = DateUtil.convertDateToString(Date()) //오늘 날짜로 초기화
        Glide.with(this@EditActivity)
            .load(IMAGE_URL + imagePath)
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(BlurTransformation(13, 1)))
            .into(viewDataBinding.editIvBlurBackground)
    }

    //로컬디비에 리뷰 저장
    private fun saveReview() {
        viewModel.downloadImage(IMAGE_URL + imagePath, "Title")

        viewModel.imagePathData.observe(this) {
            d("local imagePathData", it)
            imagePath = it
            if (it.isNotBlank()){
                val sampleReview = Review(
                    id = movie!!.id,
                    review_poster = it, //로컬 경로
                    title = viewDataBinding.editEtTitle.text.toString(),
                    date = DateUtil.convertStringToDate(viewDataBinding.editTvDate.text.toString())!!,
                    comment = viewDataBinding.editEtComment.text.toString(),
                    rating = viewDataBinding.editRatingBar.rating.toDouble(),
                    movie = movie!!
                )
                viewModel.insertReviewWithMovie(sampleReview)
            }
        }
    }

    private fun updateReview() {
        val sampleReview = Review(
            id = movie!!.id,
            review_poster = imagePath!!,
            title = viewDataBinding.editEtTitle.text.toString(),
            date = DateUtil.convertStringToDate(viewDataBinding.editTvDate.text.toString())!!,
            comment = viewDataBinding.editEtComment.text.toString(),
            rating = viewDataBinding.editRatingBar.rating.toDouble(),
            movie = movie!!
        )
        viewModel.updateReview(sampleReview)
    }

    //ToolBar에 새로 만든 menu.xml을 인플레이트함
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_edit, menu)
        setEditableMode(isFirst) //intent 를 통해 데이터를 받아온 후, 값 설정
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
            R.id.menu_save -> {
                setEditableMode(false)
                if (isFirst) saveReview() else updateReview()
                return true
            }
            R.id.menu_edit -> {
                setEditableMode(true)
                return true
            }
            R.id.menu_share -> {
                Toast.makeText(this@EditActivity, "준비 중 입니다.", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_delete -> {
                viewModel.deleteReviewByReviewId(movie!!.id)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    /**
     * false -> 저장완료 화면
     * true -> 편집 화면
     */
    private fun setEditableMode(flag: Boolean) {
        //can't edit
        viewDataBinding.editToolbar.menu.findItem(R.id.menu_share).isVisible = !flag
        viewDataBinding.editToolbar.menu.findItem(R.id.menu_edit).isVisible = !flag
        viewDataBinding.editToolbar.menu.findItem(R.id.menu_delete).isVisible = !flag

        viewDataBinding.editEtComment.isFocusable = flag
        viewDataBinding.editEtComment.isFocusableInTouchMode = flag
        viewDataBinding.editEtComment.isEnabled = flag
        d("flag ", flag.toString())
        //can edit
        viewDataBinding.editToolbar.menu.findItem(R.id.menu_save).isVisible = flag

        //https://stackoverflow.com/questions/7068873/how-can-i-disable-all-views-inside-the-layout
        //카드뷰 안에 있는 모든 뷰를 활성화 or 비활성화
        for (i in 0 until viewDataBinding.editReviewContainer.childCount) {
            val child: View = viewDataBinding.editReviewContainer.getChildAt(i)
            //레이팅바는 비활성화 하면 색깔이 투명해지므로, 색깔은 유지시키고 수정만 불가능하게 처리함.
            if(child == viewDataBinding.editRatingBar){
                viewDataBinding.editRatingBar.setIsIndicator(!flag)
            }else{
                child.isEnabled = flag
            }
        }

        if(flag) showKeyboard() else hideKeyboard()
    }

    private fun showKeyboard() {
        viewDataBinding.editEtComment.requestFocus()
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager //앱에서 창을 제어하는 기능을 제공하는 클래스. 소프트키 제어하기 위해 사용
        imm.showSoftInput(viewDataBinding.editEtComment, 0)
    }

    private fun hideKeyboard() {
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewDataBinding.editEtComment.windowToken, 0)
    }

    fun dateBtnClick() {
        showDatePickerDialog()
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this, R.style.CustomDatePickerDialogTheme,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                d("Selected Birthday", "$year  $monthOfYear  $dayOfMonth")
                // Display Selected date in textbox
                val birthDay = "${year}년 ${monthOfYear + 1}월 ${dayOfMonth}일"
                viewDataBinding.editTvDate.text = birthDay
            },
            y, m, d
        )
            .apply {
                show()
            }
    }

    fun rotateBtnClick(){
        isFront = if(isFront){
            frontCard.setTarget(cv_edit_diary_edit)
            backCard.setTarget(cv_edit_diary_poster)
            frontCard.start()
            backCard.start()
            backCardView()
            false
        } else {
            frontCard.setTarget(cv_edit_diary_poster)
            backCard.setTarget(cv_edit_diary_edit)
            frontCard.start()
            backCard.start()
            frontCardView()
            true
        }
    }

    private fun frontCardView(){
        cv_edit_diary_poster.visibility = View.GONE
        cv_edit_diary_edit.visibility = View.VISIBLE
    }

    private fun backCardView(){
        cv_edit_diary_edit.visibility = View.GONE
        cv_edit_diary_poster.visibility = View.VISIBLE
    }

}