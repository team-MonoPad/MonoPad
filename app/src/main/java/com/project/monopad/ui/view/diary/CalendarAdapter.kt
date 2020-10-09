package com.project.monopad.ui.view.diary

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.project.monopad.R
import kotlinx.android.synthetic.main.calendar_item_layout.view.*
import java.util.*

class CalendarAdapter(context: Context) : BaseAdapter() {

    private val dateList = mutableListOf<Date>()
    private val SIZE_OF_DAY = 7*5

    private var mContext : Context = context
    private var calendar = Calendar.getInstance()
    private var month = Calendar.getInstance().get(Calendar.MONTH)

    init {
        setCalendar()
    }

    override fun getView(position: Int, view: View?, p2: ViewGroup?): View {
        val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mView = view ?: layoutInflater.inflate(R.layout.calendar_item_layout, null)

        val date: Date = getItem(position) as Date
        calendar.time = date

        val itemDay = calendar.get(Calendar.DAY_OF_MONTH)
        val itemMonth = calendar.get(Calendar.MONTH)
        val itemYear = calendar.get(Calendar.YEAR)

        mView.day_tv.text = itemDay.toString()
        mView.day_tv.setTextColor(Color.WHITE)

        if(position%7==0)
            mView.day_tv.setTextColor(Color.RED)

        if(itemMonth!=month)
            mView.day_tv.setTextColor(Color.GRAY)

        return mView
    }

    override fun getItem(position: Int): Any {
        return dateList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dateList.size
    }

    fun updateCalendar(cal: Calendar){
        this.calendar = cal
        setCalendar()
        notifyDataSetChanged()
    }

    private fun setCalendar(){
        dateList.clear()

        month = calendar.get(Calendar.MONTH)

        calendar.set(Calendar.DAY_OF_MONTH, 1)
        var startOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
        calendar.add(Calendar.DAY_OF_MONTH, -startOfMonth)

        while (dateList.size < SIZE_OF_DAY) {
            dateList.add(calendar.time);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}