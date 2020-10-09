package com.project.monopad.ui.view.diary

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.marginTop
import com.project.monopad.R
import com.project.monopad.util.DateUtil
import kotlinx.android.synthetic.main.calendar_top_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarView : LinearLayout {
    private val dayOfWeek = mutableListOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    private val calendar = Calendar.getInstance()

    private var calendarAdapter = CalendarAdapter(context)
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun init() {
        orientation = VERTICAL
        calendar.set(year, month, 1, 0, 0, 0)
        addView(createTopMonthView())
        addView(createTopDayView())
        addView(createGridView())
    }

    private fun createTopMonthView(): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val topMonthView = layoutInflater.inflate(R.layout.calendar_top_layout, null)

        val format = "yyyy년 MM월"

        topMonthView.date_tv.text = DateUtil.calendarToString(calendar, format)
        topMonthView.date_tv.textSize = 18.toFloat()

        topMonthView.date_tv.setOnClickListener{
            var datePickerDialog = DatePickerDialog(context
                ,{ view, y, m, d ->
                    year = y
                    month = m
                    day = d
                    calendar.set(y, m, 1)
                    topMonthView.date_tv.text = DateUtil.calendarToString(calendar, format)
                    calendarAdapter.updateCalendar(calendar)
                }
                ,year, month, day)
                .show()
        }
        topMonthView.pre_btn.setOnClickListener {
            downToMonth()
            topMonthView.date_tv.text = DateUtil.calendarToString(calendar, format)
            calendarAdapter.updateCalendar(calendar)
        }
        topMonthView.next_btn.setOnClickListener {
            upToMonth()
            topMonthView.date_tv.text = DateUtil.calendarToString(calendar, format)
            calendarAdapter.updateCalendar(calendar)
        }
        return topMonthView
    }

    private fun createTopDayView(): LinearLayout {
        val linearLayout = LinearLayout(context)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(0,0,0,pixelToDp(10F))
        linearLayout.run {
            orientation = HORIZONTAL
            layoutParams = params
            weightSum = 7F
            setBackgroundColor(Color.TRANSPARENT)
        }

        for (i in 0 until dayOfWeek.size) {
            if ((i + 1) % 7 == 0) linearLayout.addView(createTextView(dayOfWeek[i], true))
            else linearLayout.addView(createTextView(dayOfWeek[i], false))
        }
        linearLayout.requestLayout()
        return linearLayout
    }


    private fun createGridView(): GridView {
        val gridView = GridView(context)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        gridView.setOnItemClickListener { adapterView, view, position, l ->
            val itemDate = adapterView.getItemAtPosition(position) as Date

            val calendar = Calendar.getInstance()
            calendar.time = itemDate
            val itemYear = calendar.get(Calendar.YEAR)
            val itemMonth = calendar.get(Calendar.MONTH)
            val itemDay = calendar.get(Calendar.DAY_OF_MONTH)
            calendar.set(itemYear, itemMonth, itemDay, 0, 0, 0)

            Toast.makeText(context, DateUtil.calendarToString(calendar, "yyyy.MM.dd"), Toast.LENGTH_SHORT).show()
        }
        val dp = pixelToDp(1F)

        gridView.let {
            it.layoutParams = params
            it.numColumns = 7
            it.adapter = calendarAdapter
            it.setSelector(R.drawable.calendar_list_selector)
        }
        return gridView
    }

    fun getCalendar(): Calendar {
        return calendar
    }

    private fun createTextView(text: String, lastCheck: Boolean): TextView {
        val params = LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT, 1F
        )
        val textView = TextView(context)
        textView.text = text
        textView.textSize = 16.toFloat()
        if (lastCheck) textView.setBackgroundResource(R.color.transparent)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.layoutParams = params
        return textView
    }

    private fun pixelToDp(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            resources.displayMetrics
        ).toInt()
    }


    private fun downToMonth() {
        month -= 1
        day = 1
        if (month <= 0) {
            month = 12
            year -= 1
        }
        calendar.set(year, month, day, 0, 0, 0)
    }

    private fun upToMonth() {
        month += 1
        day = 1
        if (month > 12) {
            month = 1
            year += 1
        }
        calendar.set(year, month, day, 0, 0, 0)
    }
}