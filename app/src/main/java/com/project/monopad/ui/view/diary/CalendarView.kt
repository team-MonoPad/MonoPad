package com.project.monopad.ui.view.diary

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginBottom
import com.project.monopad.R
import kotlinx.android.synthetic.main.top_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarView : LinearLayout {
    private val dayOfWeek = mutableListOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat")
    private var calendarAdapter = CalendarAdapter(context)
    private val cal = Calendar.getInstance()
    private var year = cal.get(Calendar.YEAR)
    private var month = cal.get(Calendar.MONTH)
    private var selCal = Calendar.getInstance()

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
        cal.set(year, month, 1, 0, 0, 0)
        selCal.set(year, month, 1, 0, 0, 0)
        addView(createTopDateView())
        addView(createTopView())
        addView(createGridView())
    }

    private fun createTopView(): LinearLayout {
        val l1 = LinearLayout(context)
        val lp = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        l1.run {
            orientation = HORIZONTAL
            layoutParams = lp
            weightSum = 7F
            setBackgroundColor(Color.BLACK)
            lp.bottomMargin = 15
        }

        for (i in 0 until dayOfWeek.size) {
            if ((i + 1) % 7 == 0) l1.addView(createTextView(dayOfWeek[i], true))
            else l1.addView(createTextView(dayOfWeek[i], false))
        }
        l1.requestLayout()
        return l1
    }

    private fun createTopDateView(): View {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val topDateView = inflator.inflate(R.layout.top_layout, null)

        topDateView.date_tv.text = getDate(cal)
        topDateView.date_tv.textSize = 18.toFloat()

        topDateView.pre_btn.setOnClickListener {
            downToMonth()
            topDateView.date_tv.text = getDate(cal)
            calendarAdapter.calUpdate(cal)
        }
        topDateView.next_btn.setOnClickListener {
            upToMonth()
            topDateView.date_tv.text = getDate(cal)
            calendarAdapter.calUpdate(cal)
        }
        return topDateView
    }

    private fun createGridView(): GridView {
        val gridView = GridView(context)
        val lp =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        gridView.setOnItemClickListener { adapterView, view, position, l ->
            if(adapterView.getItemAtPosition(position)!=0){
                selCal.set(year, month, adapterView.getItemAtPosition(position) as Int, 0, 0, 0)
                Toast.makeText(context,getDateFull(selCal), Toast.LENGTH_SHORT).show()
            }

        }
        val dp = pixelToDp(1F)

        gridView.let {
            it.layoutParams = lp
            it.numColumns = 7
            it.adapter = calendarAdapter
            it.setSelector(R.drawable.list_selector)
        }
        return gridView
    }

    fun getCal(): Calendar {
        return selCal
    }

    private fun createTextView(text: String, lastCheck: Boolean): TextView {
        val lp = LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT, 1F
        )
        val textView = TextView(context)
        textView.text = text
        textView.textSize = 16.toFloat()
        if (lastCheck) textView.setBackgroundResource(R.color.colorBlack)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.layoutParams = lp
        return textView
    }

    private fun pixelToDp(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            resources.displayMetrics
        ).toInt()
    }

    private fun getDate(cal: Calendar): String {
        val simpleFormat = SimpleDateFormat("yyyy년 MM월")
       return simpleFormat.format(cal.time)
    }

    private fun getDateFull(cal: Calendar): String {
        val simpleFormat = SimpleDateFormat("yyyy.MM.dd")
        return simpleFormat.format(cal.time)
    }

    private fun downToMonth() {
        month -= 1
        if (month <= 0) {
            month = 12
            year -= 1
        }
        cal.set(year, month, 1, 0, 0, 0)
    }

    private fun upToMonth() {
        month += 1
        if (month > 12) {
            month = 1
            year += 1
        }
        cal.set(year, month, 1, 0, 0, 0)
    }


}