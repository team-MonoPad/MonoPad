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
        initView()
    }

    fun initViewModel() {
        mRegisterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
            RegisterViewModel::class.java)
        mRegisterViewModel.getCurrentUser().observe(this, Observer { user->
            if(user.isValidUser){
                finish()
            }
        })
    }

    fun initBinding() {
        mBinder = DataBindingUtil.setContentView<ActivityRegisterBinding>(this, R.layout.activity_register)
        mBinder.viewModel = mRegisterViewModel
        mBinder.lifecycleOwner = this
    }

    fun initView() {
        mBinder.btnRegister.setOnClickListener{ view ->
            val name = mBinder.etName.text.toString()
            val email = mBinder.etEmail.text.toString()
            val password = mBinder.etPw.text.toString()
            val passwordCheck = mBinder.etPwCheck.text.toString()

            if (!LoginPatternCheckUtil.isValidEmail(email)) {
                Toast.makeText(this,"이메일 유효x", Toast.LENGTH_SHORT).show()
            }
            else if (!LoginPatternCheckUtil.isValidPassword(password)){
                Toast.makeText(this,"비밀번호 유효x", Toast.LENGTH_SHORT).show()
            }
            else if (!LoginPatternCheckUtil.checkPassword(password,passwordCheck)) {
                Toast.makeText(this,"비밀번호가 다릅니다", Toast.LENGTH_SHORT).show()
            }
            else {
                mRegisterViewModel.createUserWithEmailAndPassword(email,password,name)
            }
        }
    }
}