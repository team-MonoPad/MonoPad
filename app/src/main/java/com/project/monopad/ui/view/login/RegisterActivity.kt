package com.project.monopad.ui.view.login

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.project.monopad.R
import com.project.monopad.ui.viewmodel.RegisterViewModel
import com.project.monopad.databinding.ActivityRegisterBinding
import com.project.monopad.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(),
    AuthListener, EmailCheckListener {

    private val TAG = "RegisterActivity"
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
        viewModel.mRegisterListener = this
        viewModel.mEmailCheckListener = this
    }

    //AuthListener
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


    //EmailCheckListener
    override fun onEmailCheckSuccess(isEmailCheckSucccesful: Boolean) {
        val message = if(isEmailCheckSucccesful) R.string.message_is_not_duplicated else R.string.message_is_duplicated

        AlertDialog.Builder(this@RegisterActivity,android.R.style.Theme_DeviceDefault_Dialog_Alert)
            .setTitle(R.string.message_check_email_duplicate)
            .setMessage(message)
            .setPositiveButton("확인", null)
            .create()
            .show()
    }
    override fun onEmailCheckFailure(message: String) {
        Log.e(TAG,message)
    }
}