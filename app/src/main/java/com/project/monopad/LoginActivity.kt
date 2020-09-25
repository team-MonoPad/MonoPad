package com.project.monopad
import android.content.Intent
import android.os.Bundle
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
        initView()
    }

    fun initViewModel() {
        mLoginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
            LoginViewModel::class.java)
        mLoginViewModel.getCurrentUser().observe(this, Observer { user->
            if(user.isValidUser){
                startMainActivity()
            }
        })

    }

    fun initBinding() {
        mBinder = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        mBinder.viewModel = mLoginViewModel
        mBinder.lifecycleOwner = this
    }

    fun initView() {
        mBinder.loginButton.setOnClickListener{ view ->
            val email = mBinder.loginEmailEdittext.text.toString()
            val password = mBinder.loginPasswordEdittext.text.toString()

            if (!LoginPatternCheckUtil.isValidEmail(email)) {
                Toast.makeText(this,"이메일 유효x",Toast.LENGTH_SHORT).show()
            }
            else if (!LoginPatternCheckUtil.isValidPassword(password)){
                Toast.makeText(this,"비밀번호 유효x",Toast.LENGTH_SHORT).show()
            }
            else {
                mLoginViewModel.signInWithEmail(email,password)
            }

        }
    }

    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}