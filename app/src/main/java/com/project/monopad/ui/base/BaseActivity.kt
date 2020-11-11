package com.project.monopad.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.project.monopad.exception.NetworkStateHelper
import com.project.monopad.ui.view.custom.loading.ProgressDialog

abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel> : AppCompatActivity(){
    lateinit var viewDataBinding: T

    lateinit var progressDialog: ProgressDialog

    abstract val layoutResourceId: Int

    abstract val viewModel: R

    abstract fun initStartView()

    abstract fun initBeforeBinding()

    abstract fun initAfterBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)

        progressDialog = ProgressDialog(this)

        NetworkStateHelper(applicationContext, viewDataBinding.root, this)
        initStartView()
        initBeforeBinding()
        initAfterBinding()
    }
}