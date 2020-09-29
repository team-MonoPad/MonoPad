package com.project.monopad.haeseong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.project.monopad.R
import com.project.monopad.haeseong.home.HomeFragment
import com.project.monopad.haeseong.home.TestFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * https://roomedia.tistory.com/entry/kotlin-%EC%9D%B4%EC%8A%88-8-BottomNavigationView-ViewPager2-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C-%EB%A9%94%EB%89%B4-%EB%A7%8C%EB%93%A4%EA%B8%B0
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = PagerAdapter(supportFragmentManager, lifecycle)
        view_pager.registerOnPageChangeCallback( PageChangeCallback() )
        nav_view.setOnNavigationItemSelectedListener { navigationSelected(it) } //어떤 네비게이션 아이템이 선택되었는지 bottomNaviView가 알 수 있도록 리스너 등록.
    }

    /**
     *  ViewPager2는 추상 클래스인 FragmentStateAdapter를 상속한 adapter를 필요로 합니다.
     *  PageAdapter 클래스가 이 역할을 하며, 필수로 구현해야 하는 getItemCount와 createFragment만을 구현했습니다.
     */
    private inner class PagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm, lc) {
        override fun getItemCount() = 3 //사용할 프래그먼트의 수
        //createFragment 는 when 구문을 사용하여, 각각의 position 에 해당하는 fragment 를 반환합니다. 값이 이상하면 에러를 발생시킵니다.
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> TestFragment()
                2 -> TestFragment()
                else -> error("no such position: $position")
            }
        }
    }

    /**
     *  선택된 BottomNavigationView의 MenuItem을 따라 ViewPager2가 표시할 fragment를 지정합니다.
     *  setOnNavigationItemSelectedListener의 반환형은 Boolean이어야 해서 정상 실행일 때 true, 오류일 때 false를 반환합니다.
     */
    private fun navigationSelected(item: MenuItem): Boolean {
        val checked = item.setChecked(true)
        when (checked.itemId) {
            R.id.menu_home -> {
                view_pager.currentItem = 0
                return true
            }
            R.id.menu_search -> {
                view_pager.currentItem = 1
                return true
            }
            R.id.menu_default -> {
                view_pager.currentItem = 2
                return true
            }
        }
        return false
    }

    /**
     * ViewPager2를 통해 메뉴가 변경된 경우, BottomNavigationView의 아이콘 상태가 변경 되지 않습니다.
     * PageChangeCallback은 이를 해결하기 위한 클래스입니다. 선택된 페이지의 position에 따라 아이콘 상태를 변경합니다.
     *
     * 페이지의 변경이 있을 때 onPageSelected() 가 호출된다.
     */
    private inner class PageChangeCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            nav_view.selectedItemId =
                when (position) {
                    0 -> R.id.menu_home
                    1 -> R.id.menu_search
                    2 -> R.id.menu_default
                    else -> error("no such position: $position")
                }
        }
    }
}