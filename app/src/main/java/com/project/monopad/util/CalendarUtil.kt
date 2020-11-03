package com.project.monopad.util

import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil {
    fun convertCalendarToString(calendar: Calendar, format : String) : String{
        val simpleFormat = SimpleDateFormat(format)
        return simpleFormat.format(calendar.time)
    }

    fun isCalendarAndDateSame(calendar: Calendar, date : Date) : Boolean {
        val calendarOfDate = Calendar.getInstance()
        calendarOfDate.time = date
        return calendar.get(Calendar.YEAR)==calendarOfDate.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH)==calendarOfDate.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE)==calendarOfDate.get(Calendar.DATE)
    }

    fun isMonthSame(c1 : Calendar, c2 : Calendar) : Boolean {
        return c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
    }
}