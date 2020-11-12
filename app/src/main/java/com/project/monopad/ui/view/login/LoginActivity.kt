package com.project.monopad.ui.view.login

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.project.monopad.R
import com.project.monopad.databinding.ActivityLoginBinding
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(),
    AuthListener {

    override val layoutResourceId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun initStartView() {
        initGoogleSignInClient()
        initView()
        initClickEvent()
    }

    override fun initBeforeBinding() {
        viewDataBinding.lifecycleOwner = this
    }

    override fun initAfterBinding() {
        viewModel.setLoginListener(this)
    }

    private fun initGoogleSignInClient() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_REQUEST_CODE_SIGN_IN) {
            viewModel.handleGoogleSignInResult(data)
        }
    }

    private fun initView() {
        hideProgressBar()
        viewDataBinding.autoLoginButton.isChecked = true
    }

    private fun initClickEvent() {
        viewDataBinding.apply {
            autoLoginButton.setOnClickListener {
                it.isSelected = !it.isSelected
                viewModel.isAutoLoginSet = it.isSelected
            }
            loginButton.setOnClickListener {
                this@LoginActivity.viewModel.signInWithEmail(
                    viewDataBinding.loginEmailEdittext.text.toString(),
                    viewDataBinding.loginPasswordEdittext.text.toString()
                )
            }
            registerButton.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            googleLoginBtn.setOnClickListener {
                googleSignIn()
            }
        }
    }

    private fun showProgressBar() {
        viewDataBinding.progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        viewDataBinding.progressbar.visibility = View.GONE
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE_SIGN_IN)
    }

    private fun startMainActivity() {
        finish()
    }

    //AuthListener
    override fun onStarted() {
        showProgressBar()
    }

    override fun onSuccess() {
        hideProgressBar()
        startMainActivity()
    }

    override fun onFailure(message: String) {
        hideProgressBar()
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "LoginActivity"
        private const val GOOGLE_REQUEST_CODE_SIGN_IN = 9001
    }

}
