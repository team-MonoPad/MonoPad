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
import com.project.monopad.util.BaseUtil.IMAGE_URL
import com.project.monopad.util.DateUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


/**
 * Android Setup BottomNavigationView With Jetpack Navigation UI (Kotlin)
 * https://code.luasoftware.com/tutorials/android/android-setup-bottomnavigationview-with-jetpack-navigation/
 */
class EditActivity : BaseActivity<ActivityEditBinding, DiaryViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_edit

    override val viewModel: DiaryViewModel by viewModel()

    private var isFirst = true

    private lateinit var imm: InputMethodManager
    private lateinit var frontCard : AnimatorSet
    private lateinit var backCard : AnimatorSet
    private var isFront = true


    //override 된 메소드는 모두 onCreate 내에 존재함으로 activity가 시작되고 자동적으로 그려진다.
    override fun initStartView() {
        viewDataBinding.activity = this;

        //Set Toolbar
        setSupportActionBar(viewDataBinding.editToolbar) //툴바를 액션바로 등록
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //back button
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        //1. 처음 작성인지, 아닌지 -> true false
//        리뷰 작성 처음인지, 수정인지 → inntet? local db  → bolean값 받아서 db 조회/*/
        //2. 처음 작성이면, intent 값으로 받아온 영화 데이터, 배경 이미지 세팅
        //3. 처음 작성이 아니라면 , local db 에서 받아온 값으로 데이터 세팅? intent 로 세팅?

        //get Movie Data
        intent.let{
            isFront = it.getBooleanExtra("isFirst", true)
//            movie = it.getParcelableExtra<Movie>("movie")

        }

        if (intent != null){
            //받아온 값으로 영화정보, 포스터, 세팅
//            isFront = intent.getBooleanExtra("isFirst", true)
            //movie = intent.getParcelableExtra<Movie>("movie")!!
        }

        //Init Date Text
        viewDataBinding.editTvDate.text = DateUtil.simpleDateFormat.format(Date()) //오늘 날짜로 초기화

        //Init Background Poster
        Glide.with(this@EditActivity)
            .load(IMAGE_URL + "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg")
            .fitCenter()
            .apply(RequestOptions.bitmapTransform(BlurTransformation(13, 3)))
            .into(viewDataBinding.editIvBlurBackground)

        //Flip CardView Setting
        val scale : Float = applicationContext.resources.displayMetrics.density
        cv_edit_diary_edit.cameraDistance = 8000 * scale
        cv_edit_diary_poster.cameraDistance = 8000 * scale
        frontCard = AnimatorInflater.loadAnimator(applicationContext,R.animator.front_animator) as AnimatorSet
        backCard = AnimatorInflater.loadAnimator(applicationContext,R.animator.back_animator) as AnimatorSet
    }

    override fun initBeforeBinding() {

    }

    override fun initAfterBinding() {
        //observing & add item to adapter
        viewModel.isCompleted.observe(this){
            isFirst = it // Review insert 완료 시 flag 값 변경
            Toast.makeText(this@EditActivity, "리뷰 저장 성공", Toast.LENGTH_SHORT).show()

        }
    }

    //로컬디비에 리뷰 저장 (처음일 경우)
    private fun saveReview() {
        viewModel.downloadImage(IMAGE_URL + "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg", "Title")

        viewModel.imagePathData.observe(this) {
            if (it.isNotBlank()){
                val sampleMovie = Movie(
                    id = 1225,
                    poster = it,
                    title = "괴물",
                    overview = "overview",
                    release_date = "2020/08/01",
                    genres = listOf(Genre(1,"action"), Genre(2,"fantasy"))
                )
                val sampleReview = Review(
                    id = sampleMovie.id,
                    review_poster = it,
                    title = viewDataBinding.editEtTitle.text.toString(),
                    date = DateUtil.convertStringToDate(viewDataBinding.editTvDate.text.toString()),
                    comment = viewDataBinding.editEtComment.text.toString(),
                    rating = viewDataBinding.editRatingBar.rating.toDouble(),
                    movie = sampleMovie
                )
                viewModel.insertReviewWithMovie(sampleReview)
            }
        }
    }

    private fun updateReview(){
        d("review update", "update")
        Toast.makeText(this@EditActivity, "이미 저장된 리뷰가 있습니다.", Toast.LENGTH_SHORT).show()
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
                if (isFirst){
                    saveReview() //insert
                }else{
                    updateReview()
                }
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

    //날짜 클릭 시 Show DatePicker
    fun btnClick() {
        println("dateOnClick")
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
                val birthDay = "${year}-${monthOfYear + 1}-${dayOfMonth}"
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


    private fun convertViewToDrawable(): Bitmap {
        val view = viewDataBinding.editReviewContainer
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        canvas.translate((-view.scrollX).toFloat(), (-view.scrollY).toFloat())
        view.draw(canvas)
        return bitmap
    }

}