package com.project.monopad.ui.view.custom.viewpager

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.project.monopad.R
import com.project.monopad.databinding.CalendarTopLayoutBinding
import com.project.monopad.model.entity.Day
import com.project.monopad.util.CalendarUtil
import java.util.*

class CalendarView2(
    context: Context
    , attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    companion object{
        private const val PREVIOUS_MONTH = -1
        private const val NEXT_MONTH = 1
        private const val monthDateFormat = "yyyy년 MM월"
        private val dayOfWeek = mutableListOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        private val baseCalendar = Calendar.getInstance()
    }

    private lateinit var topMonthViewBinding: CalendarTopLayoutBinding

    private val viewPager: CalendarViewPager2 by lazy {
        CalendarViewPager2(context)
    }

    private val _calendarPagerAdapter : CalendarPagerAdapter2 by lazy { CalendarPagerAdapter2(context) }
    val calendarPagerAdapter: CalendarPagerAdapter2
        get() {
            return _calendarPagerAdapter
        }

    init{
        init()
    }

    private fun init() {
        orientation = VERTICAL
        addView(createTopMonthView())
        addView(createTopDayView())
        addView(createViewPager())
    }

    private var onDayClickListener: ((Int, Day) -> Unit)? = null

    fun setonDayClickListener(listener: ((Int, Day) -> Unit)) {
        this.onDayClickListener = listener
    }


    private fun createTopMonthView(): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        topMonthViewBinding = DataBindingUtil.inflate(inflater, R.layout.calendar_top_layout, this, false)

        topMonthViewBinding.dateTv.apply {
            text = CalendarUtil.convertCalendarToString(baseCalendar, monthDateFormat)
            textSize = 20F
            setOnClickListener{
                Log.e("SEULGI","연월 버튼클릭")
            }
        }
        topMonthViewBinding.preBtn.setOnClickListener {
            moveMonth(PREVIOUS_MONTH)
        }
        topMonthViewBinding.nextBtn.setOnClickListener {
            moveMonth(NEXT_MONTH)
        }
        return topMonthViewBinding.root
    }

    private fun createTopDayView(): LinearLayout {
        val linearLayout = LinearLayout(context)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            setMargins(0,10,0,10)
        }
        linearLayout.apply {
            orientation = HORIZONTAL
            layoutParams = params
            weightSum = 7F
            setBackgroundColor(Color.TRANSPARENT)
        }
        for (i in 0 until dayOfWeek.size) {
            linearLayout.addView(createTextView(dayOfWeek[i]))
        }
        linearLayout.requestLayout()
        return linearLayout
    }

    private fun createTextView(string: String): TextView {
        val params = LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT, 1F
        )
        val textView = TextView(context).apply {
            text = string
            textSize = 14F
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            layoutParams = params
            setBackgroundResource(R.color.transparent)
        }
        return textView
    }


    private fun createViewPager() : CalendarViewPager2 {
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        _calendarPagerAdapter.setonDayClickListener { month, day ->
            onDayClickListener?.invoke(month,day)
        }

        viewPager.apply {
            layoutParams = params
            adapter = _calendarPagerAdapter

            setOnPageSelectedListener { position ->
                setTopMonthText(position)
            }
        }

        return viewPager
    }

    fun moveMonth(offset : Int) {
        val cur = viewPager.currentItem+offset

        viewPager.currentItem = cur
        setTopMonthText(cur)
    }

    private fun setTopMonthText(pagerPosition : Int) {
        val cal = (baseCalendar.clone() as Calendar).apply {
            add(Calendar.MONTH, pagerPosition - CalendarPagerAdapter2.NUMBER_OF_PAGES/2)
        }

        topMonthViewBinding.dateTv.text = CalendarUtil.convertCalendarToString(cal, monthDateFormat)

    }

}