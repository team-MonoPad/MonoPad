package com.project.monopad.ui.view.edit


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
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
import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import com.project.monopad.model.network.dto.Genre
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.DiaryViewModel
import com.project.monopad.ui.viewmodel.MovieViewModel
import com.project.monopad.util.BaseUtil.IMAGE_URL
import com.project.monopad.util.DateUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.view.*
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
class EditActivity : BaseActivity<ActivityEditBinding, DiaryViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_edit

    override val viewModel: DiaryViewModel by viewModel()

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
        viewModel.getReviewByReviewId()
    }

    override fun initAfterBinding() {
        //observing & add item to adapter
    }

    //ToolBar에 새로 만든 menu.xml을 인플레이트함
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_edit, menu)
        setEditableMode(true) //intent 를 통해 데이터를 받아온 후, 값 설정
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                setEditableMode(false)
                saveReview()
                return true
            }
            R.id.menu_edit -> {
                setEditableMode(true)
                return true
            }
            R.id.menu_share -> {
                saveImageInAlbum()
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

        //https://stackoverflow.com/questions/7068873/how-can-i-disable-all-views-inside-the-layout	        if(flag) showKeyboard() else hideKeyboard()
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
                val birthDay = "${year}/${monthOfYear + 1}/${dayOfMonth}"
                viewDataBinding.editTvDate.text = birthDay
            },
            y, m, d
        )
            .apply {
                show()
            }
    }

    private fun shareFileToInstagram(uri: Uri, isVideo: Boolean) {
        val feedIntent = Intent(Intent.ACTION_SEND)
        feedIntent.type = if (isVideo) "video/*" else "image/*"
        feedIntent.putExtra(Intent.EXTRA_STREAM, uri)
        feedIntent.setPackage("com.instagram.android")
        val storiesIntent = Intent("com.instagram.share.ADD_TO_STORY")
        storiesIntent.setDataAndType(uri, if (isVideo) "mp4" else "jpg")
        storiesIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        storiesIntent.setPackage("com.instagram.android")
        this.grantUriPermission(
            "com.instagram.android", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        val chooserIntent = Intent.createChooser(feedIntent, "insta")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(storiesIntent))
        startActivity(chooserIntent)
    }
//    private fun shareStory() {
//        val backgroundAssetUri =
//            FileProvider.getUriForFile(this, "your_package_name.provider", ScreenShot())
//        val storiesIntent = Intent("com.instagram.share.ADD_TO_STORY")
//        storiesIntent.setDataAndType(backgroundAssetUri, "image/*")
//        storiesIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        storiesIntent.setPackage("com.instagram.android")
//        grantUriPermission(
//            "com.instagram.android", backgroundAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
//        )
//        this.startActivity(storiesIntent)
//    }

    private fun shareInstagramStory() {
        // Define image asset URI and attribution link URL

        // Define image asset URI and attribution link URL
        val stickerAssetUri: Uri = Uri.parse("your-image-asset-uri-goes-here")
        val attributionLinkUrl = "https://www.my-aweseome-app.com/p/BhzbIOUBval/"
        val sourceApplication = "com.my.app"

// Instantiate implicit intent with ADD_TO_STORY action,
// sticker asset, background colors, and attribution link

// Instantiate implicit intent with ADD_TO_STORY action,
// sticker asset, background colors, and attribution link
        val intent = Intent("com.instagram.share.ADD_TO_STORY")
        intent.putExtra("source_application", sourceApplication)

        intent.type = "MEDIA_TYPE_JPEG"
        intent.putExtra("interactive_asset_uri", stickerAssetUri)
        intent.putExtra("content_url", attributionLinkUrl)
        intent.putExtra("top_background_color", "#33FF33")
        intent.putExtra("bottom_background_color", "#FF00FF")

// Instantiate activity and verify it will resolve implicit intent

// Instantiate activity and verify it will resolve implicit intent
        this.grantUriPermission(
            "com.instagram.android", stickerAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        if (this.packageManager.resolveActivity(intent, 0) != null) {
            this.startActivityForResult(intent, 0)
        }
    }

    private fun convertViewToDrawable(): Bitmap {
        val view = viewDataBinding.editReviewContainer
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val b = Bitmap.createBitmap(
            bitmap,
            0,
            0,
            view.measuredWidth,
            view.measuredHeight - view.edit_toolbar.measuredHeight,
        )

        val canvas = Canvas(b)
        canvas.translate((-view.scrollX).toFloat(), (-view.scrollY).toFloat())
        view.draw(canvas)
        return bitmap
    }

    //로컬디비에 리뷰 저장
    private fun saveReview() {
        val genre = Genre(1, "action")
        val genre1 = Genre(2, "fantasy")
        val fakeMovie = Movie(
            1231,
            "post-paath",
            "title",
            "overview",
            "2020/08/01",
            listOf(genre, genre1)
        )

        val review = Review(
            review_poster = viewDataBinding.editEtTitle.text.toString(),
            title = viewDataBinding.editEtTitle.text.toString(),
            date = viewDataBinding.editTvDate.text.toString(),
            comment = viewDataBinding.editEtComment.text.toString(),
            rating = viewDataBinding.editRatingBar.rating.toDouble(),
            movie = fakeMovie
        )

        d("review", review.toString())

//        viewModel.insertReviewWithMovie(review)
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

    //https://boilerplate.tistory.com/43
    fun saveImageInAlbum() {
        val bitmap: Bitmap = convertViewToDrawable()
        val fileName = "MonoPad" + DATE_FORMAT.format(Date())
        //API 29 이상일 경우
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            with(values) {
                put(MediaStore.Images.Media.TITLE, fileName)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/MonoPad")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val uri = this.contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            val fos = this.contentResolver.openOutputStream(uri!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos?.run {
                flush()
                close()
            }
        } else { //API 29 미만일 경우
            val path = Environment.getExternalStorageDirectory().absolutePath + "/MonoPad"

//            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() +
//                    File.separator +
//                    "MonoPad"

            //처음 생성한 path 경로가 존재하는지 확인 후 존재하지 않을 시 경로를 생성
            val file = File(path)
            if (!file.exists()) {
                file.mkdirs()
                //            printToast("폴더가 생성되었습니다.");
            }

//            fos = FileOutputStream(path + "/MonoPad" + day.format(date) + ".jpeg")

            val imgFile = File(file, fileName)
            val os = FileOutputStream(path + fileName + ".jpeg")
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()

            val values = ContentValues()
            with(values) {
                put(MediaStore.Images.Media.TITLE, fileName)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.BUCKET_ID, fileName)
                put(MediaStore.Images.Media.DATA, imgFile.absolutePath)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }
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

    private fun frontCardView(){
//        edit_et_title.visibility = View.VISIBLE
//        edit_tv_date.visibility = View.VISIBLE
//        edit_rating_bar.visibility = View.VISIBLE
//        edit_et_comment.visibility = View.VISIBLE
        cv_edit_diary_poster.visibility = View.GONE
        cv_edit_diary_edit.visibility = View.VISIBLE
//        tv_movie_title.visibility = View.GONE
//        tv_movie_release_date.visibility = View.GONE
//        tv_movie_overview.visibility = View.GONE
    }

    private fun backCardView(){
//        tv_movie_title.visibility = View.VISIBLE
//        tv_movie_release_date.visibility = View.VISIBLE
//        tv_movie_overview.visibility = View.VISIBLE
//        edit_et_title.visibility = View.GONE
//        edit_tv_date.visibility = View.GONE
//        edit_rating_bar.visibility = View.GONE
//        edit_et_comment.visibility = View.GONE
        cv_edit_diary_edit.visibility = View.GONE
        cv_edit_diary_poster.visibility = View.VISIBLE
    }

}