package com.project.monopad.ui.view.custom.calendar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.project.monopad.R
import com.project.monopad.databinding.ViewCalendarTopLayoutBinding
import com.project.monopad.data.model.entity.Day
import com.project.monopad.data.model.entity.Review
import com.project.monopad.util.MainUtil.convertCalendarToString
import com.project.monopad.util.MainUtil.isMonthSame
import java.util.*

class CalendarView(
    context: Context
    , attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val baseCalendar = Calendar.getInstance()
    private val calendarViewPager: CalendarViewPager by lazy { CalendarViewPager(context) }
    private val calendarPagerAdapter: CalendarPagerAdapter by lazy { CalendarPagerAdapter(context) }

    private var onDayClickListener: ((Day) -> Unit)? = null
    private lateinit var topMonthViewBinding: ViewCalendarTopLayoutBinding

    init {
        createCalendarView()
    }

    private fun createCalendarView() {
        orientation = VERTICAL
        addView(createTopMonthView())
        addView(createTopDayView())
        addView(createViewPager())
    }

    fun setonDayClickListener(listener: ((Day) -> Unit)) {
        this.onDayClickListener = listener
    }

    fun notifyDataChanged(list: List<Review>) {
        calendarPagerAdapter.setList(list)
    }

    private fun createTopMonthView(): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        topMonthViewBinding =
            DataBindingUtil.inflate(inflater, R.layout.view_calendar_top_layout, this, false)

        topMonthViewBinding.dateTv.apply {
            text = convertCalendarToString(baseCalendar, monthDateFormat)
            textSize = 20F
            setOnClickListener {
                // top month text clicked
            }
        }
        topMonthViewBinding.preBtn.setOnClickListener {
            setViewPagerPosition(PREVIOUS_MONTH)
        }
        topMonthViewBinding.nextBtn.setOnClickListener {
            setViewPagerPosition(NEXT_MONTH)
        }
        return topMonthViewBinding.root
    }

    private fun createTopDayView(): LinearLayout {
        val linearLayout = LinearLayout(context)
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 10, 0, 10)
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

    private fun createViewPager(): CalendarViewPager {
        val params =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        calendarPagerAdapter.setonDayClickListener { calendar, day ->
            if (!isMonthSame(day.calendar, calendar)) {
                when (day.calendar.compareTo(calendar)) {
                    PREVIOUS_MONTH -> setViewPagerPosition(PREVIOUS_MONTH)
                    NEXT_MONTH -> setViewPagerPosition(NEXT_MONTH)
                }
            }
            onDayClickListener?.invoke(day)
        }

        calendarViewPager.apply {
            layoutParams = params
            adapter = calendarPagerAdapter
            setOnPageSelectedListener { position ->
                setTopMonthText(position)
            }
        }

        return calendarViewPager
    }

    private fun setViewPagerPosition(offset: Int) {
        val newPosition = calendarViewPager.currentItem + offset
        calendarViewPager.currentItem = newPosition
        setTopMonthText(newPosition)
    }

    private fun setTopMonthText(viewPagerPosition: Int) {
        val calendar = (baseCalendar.clone() as Calendar).apply {
            add(Calendar.MONTH, viewPagerPosition - CalendarPagerAdapter.NUMBER_OF_PAGES / 2)
        }
        topMonthViewBinding.dateTv.text = convertCalendarToString(calendar, monthDateFormat)
    }

    companion object {
        private const val PREVIOUS_MONTH = -1
        private const val NEXT_MONTH = 1
        private const val monthDateFormat = "yyyy년 MM월"
        private val dayOfWeek = mutableListOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    }
}