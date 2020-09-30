package com.project.monopad.ui.view
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.project.monopad.ui.AuthListener
import com.project.monopad.ui.viewmodel.LoginViewModel
import com.project.monopad.R
import com.project.monopad.databinding.ActivityLoginBinding
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override val viewModel: LoginViewModel by viewModel()

    //setting adapter or view
    override fun initStartView() {

    }

    //get data
    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
        viewModel.setLoginListener(object : AuthListener {
            override fun onStarted() {
                viewDataBinding.progressbar.visibility = View.VISIBLE
            }
            override fun onSuccess() {
                viewDataBinding.progressbar.visibility = View.GONE
                startMainActivity()
            }
            override fun onFailure(message: String) {
                viewDataBinding.progressbar.visibility = View.GONE
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    //observing & add item to adapter
    override fun initAfterBinding() {

    }


    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


/*
    //자동로그인
    override fun onStart() {
        super.onStart()
        mLoginViewModel.user?.let {
            startMainActivity()
        }
    }*/

}