package com.project.monopad.util

import com.project.monopad.model.network.dto.Genre
import com.project.monopad.model.network.response.MovieCastResponse
import com.project.monopad.model.network.response.MovieCrewResponse
import java.util.regex.Pattern

class DetailParsingUtil {

    companion object{
        private val DIRECTOR = "Director"

        fun releaseDateParsing(releaseDate: String) = releaseDate.replace("-","/")

        fun runtimeParsing(runtime: Int) = runtime.let { "${it/60}시간 ${it-(it/60)*60}분" }

        fun genreParsing(genreList: List<Genre>) : String{
            var genre = ""
            for(g in genreList){
                genre += "${g.name}, "
            }
            return genre.substring(0, genre.length-2)
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
                if(c.profile_path!=null) list.add(c)
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
}