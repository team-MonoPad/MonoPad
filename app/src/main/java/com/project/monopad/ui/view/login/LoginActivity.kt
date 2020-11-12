package com.project.monopad.ui.view.login
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.project.monopad.ui.viewmodel.LoginViewModel
import com.project.monopad.R
import com.project.monopad.databinding.ActivityLoginBinding
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.view.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(),
    AuthListener {

    private val TAG = "LoginActivity"

    override val layoutResourceId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()

    private lateinit var googleSignInOptions : GoogleSignInOptions
    private lateinit var googleSignInClient : GoogleSignInClient
    private val GOOGLE_REQUEST_CODE_SIGN_IN = 9001

    override fun initStartView() {
        initGoogleSignInClient()
        viewDataBinding.progressbar.visibility = View.GONE
    }

    override fun initBeforeBinding() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
    }

    override fun initAfterBinding() {
        viewModel.mLoginListener = this

        viewDataBinding.googleLoginBtn.setOnClickListener{
            googleSignIn()
        }
    }

    fun initGoogleSignInClient() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    override fun onStart() {
        super.onStart()

        if(viewModel.autoLogin){
            viewModel.getCurrentFirebaseUser()?.let {
                startMainActivity()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_REQUEST_CODE_SIGN_IN) {
                viewModel.handleGoogleSignInResult(data)
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun googleSignIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE_SIGN_IN)
    }

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

}
