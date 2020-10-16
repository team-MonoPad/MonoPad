package com.project.monopad.util

import com.project.monopad.model.network.dto.Genre
import com.project.monopad.model.network.response.MovieCastResponse
import com.project.monopad.model.network.response.MovieCrewResponse

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
    }
}