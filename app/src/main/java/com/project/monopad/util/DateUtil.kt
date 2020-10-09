package com.project.monopad.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun calendarToString(calendar: Calendar, format : String) : String{
        val simpleFormat = SimpleDateFormat(format)
        return simpleFormat.format(calendar.time)
    }
}