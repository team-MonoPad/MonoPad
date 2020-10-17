package com.project.monopad.ui.view.detail

import android.util.Log
import com.bumptech.glide.Glide
import com.project.monopad.R
import com.project.monopad.databinding.ActivityPersonDetailBinding
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.PersonViewModel
import com.project.monopad.util.BaseUtil
import kotlinx.android.synthetic.main.activity_person_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class PersonDetailActivity : BaseActivity<ActivityPersonDetailBinding, PersonViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_person_detail

    override val viewModel: PersonViewModel by viewModel()

    override fun initStartView() {
        // adapter set
    }

    override fun initBeforeBinding() {
        viewModel.getPersonDetail(150903)
        viewModel.getPersonDetailCredits(150903)
    }

    override fun initAfterBinding() {
        viewModel.personDetailInfo.observe(this, {
            //배우 정보 바인딩
            it.also_known_as.forEach {name_also ->
                if(Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name_also)){
                    //tv에 추가
                    Log.d("PERSON DETAIL 네임 ", "$name_also")
                }
            }
            tb_person_detail.title = it.name
            Glide.with(this)
                .load(BaseUtil.IMAGE_URL +it.profile_path)
                .fitCenter()
                .into(cv_person_detail_profile)

        })
        viewModel.personDetailMovie.observe(this, {
            it.forEach {
                Log.d("PERSON DETAIL", "${it.title}에서 ${it.character}")
            }
        })
    }


}