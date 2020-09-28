package com.project.monopad
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.monopad.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var mLoginViewModel : LoginViewModel
    private lateinit var mBinder : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBinding()
    }

/*
    //자동로그인
    override fun onStart() {
        super.onStart()
        mLoginViewModel.user?.let {
            startMainActivity()
        }
    }*/

    fun initViewModel() {
        mLoginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
            LoginViewModel::class.java)

        mLoginViewModel.setLoginListener(object : AuthListener{
            override fun onStarted() {
                mBinder.progressbar.visibility = View.VISIBLE
            }
            override fun onSuccess() {
                mBinder.progressbar.visibility = View.GONE
                startMainActivity()
            }
            override fun onFailure(message: String) {
                mBinder.progressbar.visibility = View.GONE
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun initBinding() {
        mBinder = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        mBinder.viewModel = mLoginViewModel
        mBinder.lifecycleOwner = this
    }

    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}