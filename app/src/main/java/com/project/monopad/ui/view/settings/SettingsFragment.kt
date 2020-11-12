package com.project.monopad.ui.view.settings

import android.content.Intent
import android.view.View
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.project.monopad.BuildConfig
import com.project.monopad.R
import com.project.monopad.databinding.FragmentSettingsBinding
import com.project.monopad.extension.intentAction
import com.project.monopad.extension.intentActionToUrl
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.view.custom.dialog.CheckDialog
import com.project.monopad.ui.view.login.LoginActivity
import com.project.monopad.ui.view.login.RegisterActivity
import com.project.monopad.ui.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding, LoginViewModel>(){

    override val viewModel: LoginViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_settings

    private val MONOPAD_URL = "https://github.com/team-MonoPad/MonoPad"

    override fun initStartView() {
        initClickEvent()
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    override fun onStart() {
        super.onStart()
        checkLogInState()
    }

    private fun initClickEvent() {
        viewDataBinding.apply {
            tvSignInCall.setOnClickListener {
                intentAction(LoginActivity::class)
            }
            tvSignUpCall.setOnClickListener {
                intentAction(RegisterActivity::class)
            }
            tvSignOutCall.setOnClickListener {
                showSignOutDialog()
            }
            tvContactUs.setOnClickListener {
                intentActionToUrl(MONOPAD_URL)
            }
            tvOpenSource.setOnClickListener {
                OssLicensesMenuActivity.setActivityTitle("Open Source License")
                intentAction(OssLicensesMenuActivity::class)
            }
            tvBugReport.setOnClickListener {
                sendBugEmail()
            }
        }
    }

    private fun sendBugEmail(){
        val send_address : Array<String> = arrayOf("wodbs135@gamil.com")
        val appVersion = BuildConfig.VERSION_NAME
        startActivity(
            Intent(
                Intent.ACTION_SEND
            ).setType("plain/text")
                .putExtra(Intent.EXTRA_EMAIL,send_address)
                .putExtra(Intent.EXTRA_SUBJECT, "<버그/이슈 사항 보내기>")
                .putExtra(Intent.EXTRA_TEXT,
                    "앱 버전 (AppVersion):$appVersion\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n"
                )
                .setType("message/rfc822")
        )
    }

    private fun checkLogInState() {
        if(viewModel.isLoggedIn){
            stateLoggedIn()
        }else {
            stateLoggedOut()
        }
    }

    private fun showSignOutDialog() {
        val dialog = CheckDialog(requireContext())
        dialog.setAcceptBtnOnClickListener{
            viewModel.signOut()
            stateLoggedOut()
        }
        dialog.start(getString(R.string.message_logout))
    }

    private fun stateLoggedIn() {
        viewDataBinding.apply {
            tvSignInCall.visibility = View.INVISIBLE
            tvSignUpCall.visibility = View.INVISIBLE
            tvSignOutCall.visibility = View.VISIBLE
        }
    }

    private fun stateLoggedOut() {
        viewDataBinding.apply {
            tvSignInCall.visibility = View.VISIBLE
            tvSignUpCall.visibility = View.VISIBLE
            tvSignOutCall.visibility = View.INVISIBLE
        }
    }
}