package com.project.monopad.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.project.monopad.exception.NetworkStateHelper
import com.project.monopad.ui.view.custom.loading.ProgressDialog

abstract class BaseFragment<T : ViewDataBinding, R : BaseViewModel> : Fragment(){
    lateinit var viewDataBinding: T

    lateinit var progressDialog: ProgressDialog

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
        NetworkStateHelper(requireContext(), requireView(), this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,layoutResourceId, container, false)
        progressDialog = ProgressDialog(requireContext())
        return viewDataBinding.root
    }
}