package com.project.monopad.ui.view.profile

import android.content.Intent
import android.icu.util.VersionInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.oss.licenses.OssLicensesActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.project.monopad.BuildConfig
import com.project.monopad.R
import com.project.monopad.databinding.FragmentProfileBinding
import com.project.monopad.extension.intentAction
import com.project.monopad.extension.intentActionToUrl
import com.project.monopad.extension.navigateToActivity
import com.project.monopad.extension.showToast
import com.project.monopad.ui.base.BaseFragment
import com.project.monopad.ui.view.MainActivity
import com.project.monopad.ui.view.login.LoginActivity
import com.project.monopad.ui.view.login.RegisterActivity
import com.project.monopad.ui.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, MovieViewModel>(){

    override val viewModel: MovieViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_profile

    private val MONOPAD_URL = "https://github.com/team-MonoPad/MonoPad"

    override fun initStartView() {
        viewDataBinding.apply {
            tvSignInCall.setOnClickListener {
                intentAction(LoginActivity::class)
            }
            tvSignUpCall.setOnClickListener {
                intentAction(RegisterActivity::class)
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

    fun sendBugEmail(){
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

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

}