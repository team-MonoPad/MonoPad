package com.project.monopad.ui.view.select

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.project.monopad.R
import com.project.monopad.data.model.entity.Movie
import com.project.monopad.databinding.ActivityImageSelectBinding
import com.project.monopad.extension.intentActionWithBundleSingleTop
import com.project.monopad.ui.adapter.ImageSelectAdapter
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.view.edit.EditActivity
import com.project.monopad.ui.viewmodel.ImageSelectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageSelectActivity : BaseActivity<ActivityImageSelectBinding, ImageSelectViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_image_select

    override val viewModel: ImageSelectViewModel by viewModel()

    private val INTENT_MOVIE_DATA : Movie
        get() = intent.getParcelableExtra("movie_data")!!

    private val isReselect : Boolean
        get() = intent.getBooleanExtra("isReselect", false)

    override fun initStartView() {
        toolbarSetting()
        recyclerViewSetting()
    }

    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
        viewModel.getMovieData(INTENT_MOVIE_DATA.id)
    }

    override fun initAfterBinding() {
        observeImageList()
    }

    /* observe */
    private fun observeImageList(){
        val imageSelectAdapter = ImageSelectAdapter()
        viewModel.movieImageData.observe(this, {
            viewDataBinding.rvImageSelect.adapter = imageSelectAdapter
            imageSelectAdapter.setList(it)
        })
    }

    /* view setting */
    private fun recyclerViewSetting(){
        viewDataBinding.rvImageSelect.apply {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }
    }

    private fun toolbarSetting(){
        setSupportActionBar(viewDataBinding.imageSelectToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.image_select_title)
    }

    /* toolbar menu setting */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_edit -> {
                val imageSelectAdapter = viewDataBinding.rvImageSelect.adapter as ImageSelectAdapter
                imageSelectAdapter.getImagePath().also {
                    if (it == null) {
                        Toast.makeText(this, R.string.image_select_please, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        if (isReselect) {
                            //기존 존재 -> Edit -> ImageSelect -> Edit
                            intentActionWithBundleSingleTop(EditActivity::class) {
                                putBoolean("isFirst", false)
                                putBoolean("isReselect", isReselect)
                                putParcelable("movie_data", INTENT_MOVIE_DATA)
                                putString("image_path", it)
                            }
                            finish()
                        } else {
                            //detail -> imageSelect -> Edit
                            intentActionWithBundleSingleTop(EditActivity::class){
                                putBoolean("isFirst", true)
                                putBoolean("isReselect", isReselect)
                                putString("image_path", it)
                                putParcelable("movie_data", INTENT_MOVIE_DATA)
                                if(intent!=null) putString("date", intent.getStringExtra("date"))
                            }
                            finish()
                        }

                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_image_select, menu)
        return true
    }
}