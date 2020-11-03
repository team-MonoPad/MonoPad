package com.project.monopad.ui.view.custom.calendar

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class CalendarViewPager(
    context: Context
    , attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    private var onPageSelectedListener: ((Int) -> Unit)? = null

    fun setOnPageSelectedListener(listener: ((Int) -> Unit)) {
        this.onPageSelectedListener = listener
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)

        if (adapter is CalendarPagerAdapter) {
            clearOnPageChangeListeners()
            setCurrentItem(CalendarPagerAdapter.NUMBER_OF_PAGES / 2, false)
            addOnPageChangeListener(pageChangeListener)
        }
    }

    private val pageChangeListener = object : OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {
            onPageSelectedListener?.invoke(position)
        }
    }
}