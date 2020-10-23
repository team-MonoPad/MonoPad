package com.project.monopad.ui.view.edit


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.provider.Settings.System.DATE_FORMAT
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.monopad.R
import com.project.monopad.databinding.ActivityEditBinding
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.MovieViewModel
import com.project.monopad.util.BaseUtil.IMAGE_URL
import com.project.monopad.util.DateUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Android Setup BottomNavigationView With Jetpack Navigation UI (Kotlin)
 * https://code.luasoftware.com/tutorials/android/android-setup-bottomnavigationview-with-jetpack-navigation/
 */
class EditActivity : BaseActivity<ActivityEditBinding, MovieViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_edit

    override val viewModel: MovieViewModel by viewModel()

    private lateinit var imm: InputMethodManager
    internal lateinit var frontCard : AnimatorSet
    internal lateinit var backCard : AnimatorSet
    internal var isFront = true

    //override 된 메소드는 모두 onCreate 내에 존재함으로 activity가 시작되고 자동적으로 그려진다.
    override fun initStartView() {
        viewDataBinding.activity = this;

        //Set Toolbar
        setSupportActionBar(viewDataBinding.editToolbar) //툴바를 액션바로 등록
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //back button
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        //get Movie Data
        if (intent != null){
//            movie = intent.getParcelableExtra<Movie>("movie")!!
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
        // get data
        viewModel.nowPlayMovieData()
    }

    override fun initAfterBinding() {
        //observing & add item to adapter
    }

    //ToolBar에 새로 만든 menu.xml을 인플레이트함
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_edit, menu)
        setEditableMode(true) // intent 로 저장 상태인지 편집 상태인지 데이터 받아와서 세팅
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                setEditableMode(false)
                return true
            }
            R.id.menu_edit -> {
                setEditableMode(true)
                return true
            }
            R.id.menu_share -> {
                saveImage()
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

        if(flag) showKeyboard() else hideKeyboard()

    }
    private fun showKeyboard() {
        viewDataBinding.editEtComment.requestFocus()
        imm.showSoftInput(viewDataBinding.editEtComment, 0)
    }

    private fun hideKeyboard() {
        imm.hideSoftInputFromWindow(viewDataBinding.editEtComment.windowToken, 0)
    }

    private fun convertViewToDrawable(): Bitmap {
        val view = viewDataBinding.editReviewContainer
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val c = Canvas(bitmap)
        c.translate((-view.scrollX).toFloat(), (-view.scrollY).toFloat())
        view.draw(c)
        return bitmap
    }

    //결과 화면 File 로 만들어서 return
    fun ScreenShot(): File? {
        val screenBitmap = convertViewToDrawable()
        val date = Date()
        val filename = "monopad" + DATE_FORMAT.format(date)

        val file = File(Environment.getExternalStorageDirectory().toString() + "/MonoPad", filename)
        var os: FileOutputStream? = null
        try {
            os = FileOutputStream(file)
            screenBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os) //save file
            os.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return file
    }
    fun saveImage() {
        val view = viewDataBinding.editReviewContainer

        val path = Environment.getExternalStorageDirectory().absolutePath + "/MonoPad"
        //처음 생성한 path 경로가 존재하는지 확인 후 존재하지 않을 시 경로를 생성
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
            //            printToast("폴더가 생성되었습니다.");
        }
        val date = Date()
        val day = SimpleDateFormat("yyyyMMddHHmmss")
        view.buildDrawingCache() //drawingCache에 저장
        val captureView = view.drawingCache //비트맵 형식으로 저장
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(path + "/MonoPad" + day.format(date) + ".jpeg")
            captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos) //save file
//            mResultActivityView.validateSuccessSaveImage("결과가 갤러리에 저장되었습니다.", path, day, date)
            //            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/EndGame" + day.format(date) + ".JPEG")));
            fos.flush()
            fos.close()
            view.destroyDrawingCache()
        } catch (e: IOException) {
            e.printStackTrace()
//            mResultActivityView.validateFailureSaveImage("저장 실패ㅠㅠ. [설정] > [권한] 에서 외부 접근 권한을 허용해주세요.")
        }
    }

    fun btnClick() {
        println("dateOnClick")
        showDatePickerDialog()
    }

    fun rotateBtnClick(){
        isFront = if(isFront){
            frontCard.setTarget(cv_edit_diary_edit)
            backCard.setTarget(cv_edit_diary_poster)
            frontCard.start()
            backCard.start()
            //frontCardView()
            backCardView()
            false
        } else {
            frontCard.setTarget(cv_edit_diary_poster)
            backCard.setTarget(cv_edit_diary_edit)
            frontCard.start()
            backCard.start()
            //backCardView()
            frontCardView()
            true
        }
    }

    fun frontCardView(){
        edit_et_title.visibility = View.VISIBLE
        edit_tv_date.visibility = View.VISIBLE
        edit_rating_bar.visibility = View.VISIBLE
        edit_et_comment.visibility = View.VISIBLE
        tv_movie_title.visibility = View.GONE
        tv_movie_release_date.visibility = View.GONE
        tv_movie_overview.visibility = View.GONE
    }

    fun backCardView(){
        tv_movie_title.visibility = View.VISIBLE
        tv_movie_release_date.visibility = View.VISIBLE
        tv_movie_overview.visibility = View.VISIBLE
        edit_et_title.visibility = View.GONE
        edit_tv_date.visibility = View.GONE
        edit_rating_bar.visibility = View.GONE
        edit_et_comment.visibility = View.GONE

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
        ).apply {
            show()
        }
    }

}