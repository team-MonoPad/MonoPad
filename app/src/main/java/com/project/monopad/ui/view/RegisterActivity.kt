package com.project.monopad.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.project.monopad.ui.AuthListener
import com.project.monopad.R
import com.project.monopad.ui.viewmodel.RegisterViewModel
import com.project.monopad.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var mRegisterViewModel : RegisterViewModel
    private lateinit var mBinder : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBinding()
    }

    fun initViewModel() {
        mRegisterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
            RegisterViewModel::class.java)

        mRegisterViewModel.setRegisterListener(object :
            AuthListener {
            override fun onStarted() {
                mBinder.progressbar.visibility = View.VISIBLE
            }
            override fun onSuccess() {
                mBinder.progressbar.visibility = View.GONE
                finish()
            }
            override fun onFailure(message: String) {
                mBinder.progressbar.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
            }
        })

        mRegisterViewModel.setEmailCheckListener(object :
            RegisterViewModel.EmailCheckListener {
            override fun onSuccess(isEmailCheckSucccesful: Boolean) {
                lateinit var messege : String
                if(isEmailCheckSucccesful) messege = "중복되지않음"
                else  messege = "중복됨"

                val alertDialog = AlertDialog.Builder(this@RegisterActivity)
                    .setTitle("이메일 중복확인")
                    .setMessage(messege)
                    .setPositiveButton("확인", null)
                    .create()
                    .show()
            }
            override fun onFailure(message: String) { }
        })

    }

    fun initBinding() {
        mBinder = DataBindingUtil.setContentView<ActivityRegisterBinding>(this,
            R.layout.activity_register
        )
        mBinder.viewModel = mRegisterViewModel
        mBinder.lifecycleOwner = this
    }

}