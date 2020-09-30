package com.project.monopad.ui.view


import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.project.monopad.R
import com.project.monopad.databinding.ActivityMainBinding
import com.project.monopad.ui.base.BaseActivity
import com.project.monopad.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Android Setup BottomNavigationView With Jetpack Navigation UI (Kotlin)
 * https://code.luasoftware.com/tutorials/android/android-setup-bottomnavigationview-with-jetpack-navigation/
 */
class MainActivity : BaseActivity<ActivityMainBinding, MovieViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override val viewModel: MovieViewModel by viewModel()

    //override 된 메소드는 모두 onCreate 내에 존재함으로 activity가 시작되고 자동적으로 그려진다.
    override fun initStartView() {
        //setting adapter or view

        setSupportActionBar(viewDataBinding.mainToolbar) //툴바를 액션바로 등록
        setUpBottomNavigationView()

    }

    override fun initBeforeBinding() {
        // get data
        viewModel.nowPlayMovieData()
    }

    override fun initAfterBinding() {
        //observing & add item to adapter
    }

    private fun setUpBottomNavigationView(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val bottomNavDestinationIds = setOf(
            R.id.navigation_home, R.id.navigation_search, R.id.navigation_diary, R.id.navigation_profile
        )
        val appBarConfig = AppBarConfiguration(bottomNavDestinationIds)
        setupActionBarWithNavController(navController, appBarConfig) //Toolbar 에 자동으로 탭명 세팅 + 탭 클릭시 프래그먼트로 이동
        viewDataBinding.mainBottomNavView.setupWithNavController(navController)

//         make sure appbar/toolbar is not hidden upon fragment switch ?
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in bottomNavDestinationIds) {
                viewDataBinding.mainAppBarLayout.setExpanded(true, true)
            }
        }
    }



}