package com.project.monopad.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil{
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA)

    fun getDayDifference(releaseDate: String): String {
        val date = simpleDateFormat.parse(releaseDate)
        val today = Calendar.getInstance()
        val diffDate = (today.time.time - date.time) / (60 * 60 * 24 * 1000)
        if (diffDate>=0){
            return "+$diffDate"
        }
        return diffDate.toString()
    }
}