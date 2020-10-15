package com.project.monopad.ui.view.review

import android.content.Intent
import android.view.MenuItem
import com.project.monopad.R
import com.project.monopad.databinding.ActivityImageSelectBinding
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.ImageSelectViewModel
import kotlinx.android.synthetic.main.activity_image_select.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageSelectActivity : BaseActivity<ActivityImageSelectBinding, ImageSelectViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_image_select

    override val viewModel: ImageSelectViewModel by viewModel()


    override fun initStartView() {
        toolbarSetting()
    }

    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this

    }

    override fun initAfterBinding() {
        //
    }


    private fun toolbarSetting(){
        setSupportActionBar(image_select_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    /* toolbar menu setting */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}