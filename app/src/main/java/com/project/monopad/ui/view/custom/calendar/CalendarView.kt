package com.project.monopad.ui.view.custom.calendar

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.project.monopad.R
import com.project.monopad.model.entity.Day
import com.project.monopad.util.CalendarUtil
import kotlinx.android.synthetic.main.calendar_top_layout.view.*
import java.util.*

class CalendarView : LinearLayout {

    private val monthDateFormat = "yyyy년 MM월"
    private val dayOfWeek = mutableListOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    private val calendar = Calendar.getInstance()

    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DATE)

    private var onDayClickListener: ((Int, Day) -> Unit)? = null

    private lateinit var topMonthView : View

    val calendarAdapter =
        CalendarAdapter(context)

    fun setonDayClickListener(listener: ((Int, Day) -> Unit)) {
        this.onDayClickListener = listener
    }

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
        topMonthView = layoutInflater.inflate(R.layout.calendar_top_layout, null)

        topMonthView.date_tv.apply {
            text = CalendarUtil.convertCalendarToString(calendar, monthDateFormat)
            textSize = 20F
            setOnClickListener{
                DatePickerDialog(context, R.style.myDialogTheme
                    ,{ view, y, m, d ->
                        onCalendarUpdated(y,m,1)
                    }
                    ,year, month, day)
                    .show()
            }
        }
        topMonthView.pre_btn.setOnClickListener {
            moveMonth(-1)
        }
        topMonthView.next_btn.setOnClickListener {
            moveMonth(1)
        }
        return topMonthView
    }

    private fun createTopDayView(): LinearLayout {
        val linearLayout = LinearLayout(context)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            setMargins(0,10,0,10)
        }

        linearLayout.run {
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

    private fun createGridView(): GridView {
        val gridView = GridView(context)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        gridView.apply {
            layoutParams = params
            numColumns = 7
            adapter = calendarAdapter
            setSelector(R.drawable.calendar_list_selector)
            setOnItemClickListener { adapterView, view, position, l ->
                val item = adapterView.getItemAtPosition(position) as Day
                onDayClickListener?.invoke(month, item)
            }
        }

        calendarAdapter.updateCalendar(calendar)
        return gridView
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

    fun notifyCalendarChanged() {
        calendarAdapter.updateCalendar(calendar.apply {
            set(year, month, day, 0, 0, 0)
        })
    }

    fun onCalendarUpdated(itemYear : Int, itemMonth : Int, itemDay : Int) {
        this.year = itemYear
        this.month = itemMonth
        this.day = itemDay
        onMonthUpdated(itemYear,itemMonth,itemDay)
    }

    private fun moveMonth(value : Int) {
        month += value
        day = 1
        if (month < 0) {
            month = 11
            year -= 1
        }
        if (month > 11) {
            month = 0
            year += 1
        }
        onMonthUpdated(year, month, day)
    }

    private fun onMonthUpdated(itemYear : Int, itemMonth : Int, itemDay : Int) {
        calendar.set(itemYear, itemMonth, itemDay, 0, 0, 0)
        topMonthView.date_tv.text = CalendarUtil.convertCalendarToString(calendar, monthDateFormat)
        calendarAdapter.updateCalendar(calendar)
    }
}