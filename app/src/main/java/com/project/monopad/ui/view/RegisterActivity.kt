package com.project.monopad.ui.view

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding4.widget.textChanges
import com.project.monopad.ui.AuthListener
import com.project.monopad.R
import com.project.monopad.ui.viewmodel.RegisterViewModel
import com.project.monopad.databinding.ActivityRegisterBinding
import com.project.monopad.ui.EmailCheckListener
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.util.LoginPatternCheckUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_register
    override val viewModel: RegisterViewModel by viewModel()

    override fun initStartView() {
        viewDataBinding.progressbar.visibility = View.GONE
    }

    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
    }

    override fun initAfterBinding() {
        viewModel.setRegisterListener(object : AuthListener {
            override fun onStarted() {
                viewDataBinding.progressbar.visibility = View.VISIBLE
            }
            override fun onSuccess() {
                viewDataBinding.progressbar.visibility = View.GONE
                finish()
            }
            override fun onFailure(message: String) {
                viewDataBinding.progressbar.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.setEmailCheckListener(object : EmailCheckListener {
            override fun onSuccess(isEmailCheckSucccesful: Boolean) {
                lateinit var messege : String
                if(isEmailCheckSucccesful) messege = "중복되지않음"
                else  messege = "중복됨 다시 입력 ㄱㄱ"

                val alertDialog = AlertDialog.Builder(this@RegisterActivity)
                    .setTitle("이메일 중복확인")
                    .setMessage(messege)
                    .setPositiveButton("확인", null)
                    .create()
                    .show()
            }
            override fun onFailure(message: String) {
                Log.e("REGISTER",message)
            }
        })
    }

}