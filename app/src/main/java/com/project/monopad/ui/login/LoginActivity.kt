package com.project.monopad.ui.login
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.monopad.MainActivity
import com.project.monopad.R
import com.project.monopad.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var mLoginViewModel : LoginViewModel
    private lateinit var mBinder : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBinding()
        initView()
    }

    fun initBinding() {
        mBinder = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        mBinder.viewModel = mLoginViewModel
        mBinder.lifecycleOwner = this
    }

    fun initViewModel() {
        mLoginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(LoginViewModel::class.java)
        mLoginViewModel.getCurrentUser().observe(this, Observer { user->
            if(user.isValidUser){
                startMainActivity()
            }
        })

    }

    fun initView() {
        mBinder.loginButton.setOnClickListener{ view ->
            val email = mBinder.loginEmailEdittext.text.toString()
            val password = mBinder.loginPasswordEdittext.text.toString()

            if (!email.isValidEmail()) {
                Toast.makeText(this,"이메일 유효x",Toast.LENGTH_SHORT).show()
            }
            else if (!isValidPassword(password)){
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

    fun String?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun isValidPassword(password : String?): Boolean {
        val PASSWORD_PATTERN: Pattern = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{6,24}"
        )
        return !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches()
    }
}