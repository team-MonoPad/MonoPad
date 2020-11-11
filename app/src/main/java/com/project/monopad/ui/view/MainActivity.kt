package com.project.monopad.ui.view

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.monopad.R
import com.project.monopad.databinding.ActivityMainBinding
import com.project.monopad.extension.replace
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.view.diary.DiaryFragment
import com.project.monopad.ui.view.home.HomeFragment
import com.project.monopad.ui.view.search.SearchFragment
import com.project.monopad.ui.view.settings.SettingsFragment
import com.project.monopad.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MovieViewModel>(){

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override val viewModel: MovieViewModel by viewModel()

    private val homeFragment : HomeFragment by lazy {HomeFragment()}
    private val searchFragment: SearchFragment by lazy{SearchFragment()}
    private val diaryFragment : DiaryFragment by lazy{DiaryFragment()}
    private val settingsFragment : SettingsFragment by lazy{SettingsFragment()}

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item->
        when(item.itemId){
            R.id.navigation_home->{
                replace(R.id.container, homeFragment)
                viewDataBinding.mainToolbar.title = getString(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search->{
                replace(R.id.container, searchFragment)
                viewDataBinding.mainToolbar.title = getString(R.string.title_search)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_diary->{
                replace(R.id.container, diaryFragment)
                viewDataBinding.mainToolbar.title = getString(R.string.title_diary)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile->{
                replace(R.id.container, settingsFragment)
                viewDataBinding.mainToolbar.title = getString(R.string.title_settings)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun initStartView() {
        setSupportActionBar(viewDataBinding.mainToolbar)
        replace(R.id.container, homeFragment)
        viewDataBinding.mainBottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun initBeforeBinding() {}

    override fun initAfterBinding() {}

}