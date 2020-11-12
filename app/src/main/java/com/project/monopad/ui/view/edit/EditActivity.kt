package com.project.monopad.ui.view.edit


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.DatePickerDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.observe
import com.project.monopad.R
import com.project.monopad.data.model.entity.Movie
import com.project.monopad.data.model.entity.Review
import com.project.monopad.databinding.ActivityEditBinding
import com.project.monopad.extension.intentActionWithBundle
import com.project.monopad.extension.showToast
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.view.custom.dialog.CheckDialog
import com.project.monopad.ui.view.select.ImageSelectActivity
import com.project.monopad.ui.viewmodel.DiaryViewModel
import com.project.monopad.util.BaseUtil.IMAGE_URL
import com.project.monopad.util.DateUtil
import com.project.monopad.util.DetailParsingUtil
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class EditActivity : BaseActivity<ActivityEditBinding, DiaryViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_edit

    override val viewModel: DiaryViewModel by viewModel()

    var isFirst = true
    private lateinit var movie : Movie
    var imagePath: String? = null
    private var mSelectedDate: String? = null
    private lateinit var imm: InputMethodManager
    private lateinit var frontCard : AnimatorSet
    private lateinit var backCard : AnimatorSet
    private var isFront = true
    private var isReselect = false




    override fun initStartView() {
        viewDataBinding.activity = this
        initToolbar()

        intent?.apply {
            isFirst = getBooleanExtra("isFirst",false)
            isReselect = getBooleanExtra("isReselect",false)
            imagePath = getStringExtra("image_path")
            movie = getParcelableExtra("movie_data")!!
            mSelectedDate = getStringExtra("date")
        }


        if (isFirst){ //첫 작성이라면
            setFirstReview()
        }else{ //첫 작성이 아니라면
            if (isReselect){ //재선택이라면
               //image 재선택

            }else{ //재선택이 아니라면
                viewModel.getReviewByReviewId(id = movie.id)
            }
        }


        //Flip CardView Setting
        applicationContext.resources.displayMetrics.density.let {
            viewDataBinding.cvEditDiaryEdit.cameraDistance = it * 8000
            viewDataBinding.cvEditDiaryPoster.cameraDistance = it * 8000
        }
        frontCard = AnimatorInflater.loadAnimator(applicationContext,R.animator.front_animator) as AnimatorSet
        backCard = AnimatorInflater.loadAnimator(applicationContext,R.animator.back_animator) as AnimatorSet
    }

    override fun initBeforeBinding() {}

    //observing & add item to adapter\
    override fun initAfterBinding() {
        //리뷰 저장 or 수정 응답 결과
        viewModel.isCompleted.observe(this){
            isFirst = !it // insert/update 완료 : true -> isFirst = false
            if(!it) finish() //delete 완료 : false -> 종료
        }

        //이미 저장한 리뷰가 있다면, 받아온 데이터로 View 세팅
        viewModel.singleReviewData.observe(this){
            viewDataBinding.run{
                review = it
                movie = it.movie
                editTvDate.text = DateUtil.convertDateToString(it.date)
                it.movie.genres?.run{
                    editMovieTvGenre.text = DetailParsingUtil.genreParsing(this)
                }
            }

            imagePath = it.review_poster
        }
    }

    private fun setFirstReview(){
        imagePath = IMAGE_URL + imagePath
        viewDataBinding.movie = movie
        if (mSelectedDate.isNullOrBlank()){
            viewDataBinding.editTvDate.text = DateUtil.convertDateToString(Date()) //오늘 날짜로 초기화
        }else{
            viewDataBinding.editTvDate.text = mSelectedDate
        }
        movie.genres?.run{
            viewDataBinding.editMovieTvGenre.text = DetailParsingUtil.genreParsing(this)
        }
    }

    private fun saveReview() {
        viewModel.downloadImage(imagePath!!, movie.title!!)

        viewModel.imagePathData.observe(this) {
            imagePath = it
            if (it.isNotBlank()){
                val review = Review(
                    id = movie.id,
                    review_poster = it, //로컬 경로
                    title = viewDataBinding.editEtTitle.text.toString(),
                    date = DateUtil.convertStringToDate(viewDataBinding.editTvDate.text.toString())!!,
                    comment = viewDataBinding.editEtComment.text.toString(),
                    rating = viewDataBinding.editRatingBar.rating.toDouble(),
                    movie = movie
                )
                viewModel.insertReviewWithMovie(review)
            }
        }
    }

    private fun updateReviewWithNewPoster(imgPath : String){
        viewModel.downloadImage(imgPath, movie.title!!)

        viewModel.imagePathData.observe(this) {
            imagePath = it
            if (it.isNotBlank()){
                val review = Review(
                    id = movie.id,
                    review_poster = it, //로컬 경로
                    title = viewDataBinding.editEtTitle.text.toString(),
                    date = DateUtil.convertStringToDate(viewDataBinding.editTvDate.text.toString())!!,
                    comment = viewDataBinding.editEtComment.text.toString(),
                    rating = viewDataBinding.editRatingBar.rating.toDouble(),
                    movie = movie
                )
                viewModel.insertReviewWithMovie(review)
            }
        }
    }

    private fun updateReview() {
        val review = Review(
            id = movie.id,
            review_poster = imagePath!!,
            title = viewDataBinding.editEtTitle.text.toString(),
            date = DateUtil.convertStringToDate(viewDataBinding.editTvDate.text.toString())!!,
            comment = viewDataBinding.editEtComment.text.toString(),
            rating = viewDataBinding.editRatingBar.rating.toDouble(),
            movie = movie
        )
        viewModel.updateReview(review)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_edit, menu)
        setEditableMode(isFirst) //intent 를 통해 데이터를 받아온 후, 값 설정
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
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
                showToast(this, "서비스 준비중입니다.")
                return true
            }
            R.id.menu_delete -> {
                showDeleteDialog()
                return true
            }

            R.id.menu_reselect_image -> {
                intentActionWithBundle(ImageSelectActivity::class){
                    putBoolean("isReselect",true)
                    putParcelable("movie_data", movie)
                }
                finish()
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
        viewDataBinding.run {
            //can't edit
            editToolbar.menu.findItem(R.id.menu_share).isVisible = !flag
            editToolbar.menu.findItem(R.id.menu_share).isVisible = !flag
            editToolbar.menu.findItem(R.id.menu_edit).isVisible = !flag
            editToolbar.menu.findItem(R.id.menu_more_vert).isVisible = !flag
            editToolbar.menu.findItem(R.id.menu_delete).isVisible = !flag
            editToolbar.menu.findItem(R.id.menu_reselect_image).isVisible = !flag
            editEtComment.isFocusable = flag
            editEtComment.isFocusableInTouchMode = flag
            editEtComment.isEnabled = flag

            //can edit
            editToolbar.menu.findItem(R.id.menu_save).isVisible = flag
        }

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
        viewDataBinding.editEtTitle.requestFocus()
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager //앱에서 창을 제어하는 기능을 제공하는 클래스. 소프트키 제어하기 위해 사용
        imm.showSoftInput(viewDataBinding.editEtTitle, 0)
    }

    private fun hideKeyboard() {
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewDataBinding.editEtTitle.windowToken, 0)
    }

    fun dateBtnClick() {
        showDatePickerDialog()
    }
    
    private fun initToolbar(){
        setSupportActionBar(viewDataBinding.editToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_left)
        supportActionBar?.elevation = 4.0f
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this, R.style.CustomDatePickerDialogTheme,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val birthDay = "${year}년 ${monthOfYear + 1}월 ${dayOfMonth}일"
                viewDataBinding.editTvDate.text = birthDay
            },
            y, m, d
        ).show()
    }

    private fun showDeleteDialog(){
        val dialog = CheckDialog(this)
        dialog.setAcceptBtnOnClickListener{
            viewModel.deleteReviewByReviewId(movie.id)
        }
        dialog.start(getString(R.string.dialog_delete_message))
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