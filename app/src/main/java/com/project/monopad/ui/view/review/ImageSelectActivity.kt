package com.project.monopad.ui.view.review

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.project.monopad.R
import com.project.monopad.databinding.ActivityImageSelectBinding
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.adapter.ImageSelectAdapter
import com.project.monopad.ui.viewmodel.ImageSelectViewModel
import kotlinx.android.synthetic.main.activity_image_select.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageSelectActivity : BaseActivity<ActivityImageSelectBinding, ImageSelectViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_image_select

    override val viewModel: ImageSelectViewModel by viewModel()


    override fun initStartView() {
        toolbarSetting()
        recyclerViewSetting()
    }

    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
        viewModel.getMovieData(intent?.getIntExtra("movie_id", 89501) ?: 89501)

    }

    override fun initAfterBinding() {
        observeImageList()
    }

    /* observe */
    private fun observeImageList(){
        val imageSelectAdapter = ImageSelectAdapter()
        viewModel.movieImageData.observe(this, {
            rv_image_select.adapter = imageSelectAdapter
            imageSelectAdapter.setList(it)
        })
    }

    /* view setting */
    private fun recyclerViewSetting(){
        rv_image_select.apply {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }
    }

    private fun toolbarSetting(){
        setSupportActionBar(image_select_toolbar)
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
            R.id.action_review -> {
                val imageSelectAdapter = rv_image_select.adapter as ImageSelectAdapter
                imageSelectAdapter.getImagePath().also {
                    if(it==null){
                        Toast.makeText(this, R.string.image_select_please, Toast.LENGTH_SHORT).show()
                    } else {
                        // go to review edit view
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