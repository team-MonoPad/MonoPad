package com.project.monopad.ui.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.project.monopad.exception.NetworkStateHelper

abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel> : AppCompatActivity(){
    lateinit var viewDataBinding: T

    abstract val layoutResourceId: Int

    abstract val viewModel: R

    abstract fun initStartView()

    abstract fun initBeforeBinding()

    abstract fun initAfterBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)

        NetworkStateHelper(applicationContext, viewDataBinding.root, this)
        initStartView()
        initBeforeBinding()
        initAfterBinding()

    }
}