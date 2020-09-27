package com.project.monopad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.monopad.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var mRegisterViewModel : RegisterViewModel
    private lateinit var mBinder : ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBinding()
        //initView()
    }

    fun initViewModel() {
        mRegisterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
            RegisterViewModel::class.java)

        mRegisterViewModel.getCurrentUser().observe(this, Observer { user->
            finish()
        })

        mRegisterViewModel.showErrorToast.observe(this, Observer {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun initBinding() {
        mBinder = DataBindingUtil.setContentView<ActivityRegisterBinding>(this, R.layout.activity_register)
        mBinder.viewModel = mRegisterViewModel
        mBinder.lifecycleOwner = this
    }

    fun initView() {
/*        mBinder.btnRegister.setOnClickListener{ view ->
            val name = mBinder.etName.text.toString()
            val email = mBinder.etEmail.text.toString()
            val password = mBinder.etPw.text.toString()
            val passwordCheck = mBinder.etPwCheck.text.toString()

            mRegisterViewModel.createUserWithEmailAndPassword(email,password,passwordCheck,name)
        }*/
    }
}