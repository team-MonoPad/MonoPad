package com.project.monopad.ui.view.custom.viewpager

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.project.monopad.R
import com.project.monopad.model.entity.Day
import com.project.monopad.model.entity.Review
import com.project.monopad.util.CalendarUtil
import java.util.*

class CalendarGridAdapter(private val context: Context, private val position: Int) : BaseAdapter() {

    companion object{
        private const val SIZE_OF_DAY = 7*6
        private val reviewList = mutableListOf<Review>()
    }
    private val dayList = mutableListOf<Day>()
    private var calendar : Calendar
    private var month : Int

    //var month = calendar.get(Calendar.MONTH)

    /*fun setCalendar(calendar: Calendar){
        this.calendar = calendar
    }*/

    //private var calendar = Calendar.getInstance()
//    private var month = calendar.get(Calendar.MONTH)
    //private var day = Calendar.getInstance().get(Calendar.DATE)

    init {
        calendar = getCalendar(position)
        month = calendar.get(Calendar.MONTH)
        setCalendar()
    }

    fun setList(list : List<Review>) {
        reviewList.apply {
            clear()
            addAll(list)
        }
        Log.e("SEULGI grid list",""+reviewList.size)
        setCalendar()
        notifyDataSetChanged()
    }

    /*fun updateCalendar(cal: Calendar){
        this.calendar = cal
        setCalendar()
        notifyDataSetChanged()
    }*/

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mView = view ?: layoutInflater.inflate(R.layout.calendar_item_layout, null)

        val dayTv : TextView = mView.findViewById(R.id.day_tv)
        val posterIv : ImageView = mView.findViewById(R.id.poster_iv)

        val day: Day = getItem(position) as Day

        val itemYear = day.year
        val itemMonth = day.month
        val itemDay = day.day


        //calendar.set(itemYear, itemMonth, itemDay,0,0,0)

        dayTv.apply {
            text = itemDay.toString()
            when(position%7) {
                0 -> setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                6 -> setTextColor(ContextCompat.getColor(context, R.color.colorBlue))
                else -> setTextColor(Color.WHITE)
            }
        }

        val alphaValue = if(itemMonth!=month) 0.4F else 1.0F
        dayTv.alpha = alphaValue
        posterIv.alpha = alphaValue

        if(day.reviews.isNotEmpty()){
            posterIv.visibility = View.VISIBLE

            Glide.with(mView.context)
                .load(day.reviews.get(0).review_poster)
                .into(posterIv)
        }
        else {
            posterIv.visibility = View.GONE
        }

        return mView
    }

    override fun getItem(position: Int): Any = dayList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = dayList.size

    fun setCalendar(){
        dayList.clear()

        val cal = getCalendar(position)
        month = cal.get(Calendar.MONTH)


        cal.set(Calendar.DATE, 1)
        val startOfMonth = cal.get(Calendar.DAY_OF_WEEK) - 1
        cal.add(Calendar.DATE, -startOfMonth)

        while (dayList.size < SIZE_OF_DAY) {
            val it = reviewList.iterator()
            val reviews = mutableListOf<Review>()
            while(it.hasNext()){
                val item = it.next()
                if(CalendarUtil.isCalendarAndDateSame(cal, item.date)){
                    reviews.add(item)
                }
            }
            dayList.add(Day(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE),reviews))
            cal.add(Calendar.DATE, 1)
        }
    }


    fun getCalendar(position: Int): Calendar {
        return Calendar.getInstance().apply {
            add(Calendar.MONTH, position - CalendarPagerAdapter2.NUMBER_OF_PAGES / 2)
        }
    }
}