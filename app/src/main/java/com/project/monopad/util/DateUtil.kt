package com.project.monopad.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil{
    private val simpleDateFormat1 = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA) //review format
    private val simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA) //movie dto format

    fun getDayDifference(releaseDate: String): String {
        val date = simpleDateFormat2.parse(releaseDate)
        val today = Calendar.getInstance()
        val diffDate = (today.time.time - date.time) / (60 * 60 * 24 * 1000)
        return when {
            diffDate > 0 -> {
                "+$diffDate"
            }
            diffDate < 0 -> {
                diffDate.toString()
            }
            else -> {
                "-Day"
            }
        }
    }

    fun convertDateToString(date : Date) : String
        = simpleDateFormat1.format(date)

    fun convertStringToDate(stDate : String) : Date?
        = simpleDateFormat1.parse(stDate)
}