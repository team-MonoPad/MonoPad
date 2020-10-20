package com.project.monopad.ui.base

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding, R : BaseViewModel> : Fragment(){
    lateinit var viewDataBinding: T

    abstract val layoutResourceId: Int

    abstract val viewModel: R

    abstract fun initStartView()

    abstract fun initDataBinding()

    abstract fun initAfterBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,layoutResourceId, container, false)
        val view = viewDataBinding.root

        return view
    }

    private fun progressOn(activity: Activity, layoutId:Int) {

    }

    private fun progressOff() {

    }

    /**
     * isProgress(activity as Activity, R.layout.progress_bar, true/false)
     */
    fun isProgress(activity: Activity, layoutId: Int, flag: Boolean) {
        if(flag){
            progressOn(activity, layoutId)
        }else{
            progressOff()
        }
    }


}