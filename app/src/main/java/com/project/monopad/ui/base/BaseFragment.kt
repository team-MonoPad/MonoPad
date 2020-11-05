package com.project.monopad.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.project.monopad.exception.NetworkStateHelper

abstract class BaseFragment<T : ViewDataBinding, R : BaseViewModel> : Fragment(){
    lateinit var viewDataBinding: T

    abstract val layoutResourceId: Int

    abstract val viewModel: R

    abstract fun initStartView()

    abstract fun initDataBinding()

    abstract fun initAfterBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NetworkStateHelper(requireContext(), requireView(), this)
        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding= DataBindingUtil.inflate(inflater,layoutResourceId, container, false)
        return viewDataBinding.root
    }
}