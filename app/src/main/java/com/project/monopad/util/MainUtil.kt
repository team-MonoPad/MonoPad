package com.project.monopad.util

import com.project.monopad.data.model.dto.Genre
import com.project.monopad.data.model.network.response.MovieCastResponse
import com.project.monopad.data.model.network.response.MovieCrewResponse
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object MainUtil {
    fun convertCalendarToString(calendar: Calendar, format : String) : String{
        val simpleFormat = SimpleDateFormat(format, Locale.KOREA)
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

    private val DIRECTOR = "Director"

    fun releaseDateParsing(releaseDate: String) = releaseDate.replace("-","/")

    fun runtimeParsing(runtime: Int) = runtime.let { "${it/60}시간 ${it-(it/60)*60}분" }

    fun genreParsing(genreList: List<Genre>) : String{
        var genre = ""
        for(g in genreList){
            genre += "${g.name}, "
        }
        return if(genre.length >= 2) genre.substring(0, genre.length-2) else ""

    }

    fun directorParsing(crewList : List<MovieCrewResponse>) : String{
        for(crew in crewList){
            if(crew.job == DIRECTOR)  return "Director : ${crew.name}"
        }
        return ""
    }

    fun casterParsing(casters: List<MovieCastResponse>): List<MovieCastResponse> {
        val list = ArrayList<MovieCastResponse>()
        for(c in casters){
            list.add(c)
        }
        return list
    }

    fun koreaNameParsing(data : List<String>) : String {
        var test = ""
        data.forEach { name_also ->
            if (Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name_also)) {
                test += "#$name_also "
            }
        }
        return test
    }
}