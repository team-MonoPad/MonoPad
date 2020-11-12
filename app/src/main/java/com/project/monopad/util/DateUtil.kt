package com.project.monopad.util

import com.project.monopad.data.model.network.response.MovieInfoResultResponse
import java.text.SimpleDateFormat
import java.util.*

object DateUtil{
    private val simpleDateFormat1 = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA) //review format
    private val simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA) //movie dto format
    private val fileNameDateFormat = SimpleDateFormat("yyMMddHHmmss", Locale.KOREA) //movie dto format

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

    fun getFileNameDate(date : Date) : String
        = fileNameDateFormat.format(date)

    fun convertStringToDate(stDate : String) : Date?
        = simpleDateFormat1.parse(stDate)

    class DateComparator : Comparator<MovieInfoResultResponse?> {
        override fun compare(o1: MovieInfoResultResponse?, o2: MovieInfoResultResponse?): Int {
            return o1!!.release_date.compareTo(o2!!.release_date)
        }
    }
}