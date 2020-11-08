package com.project.monopad.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.monopad.data.model.entity.Movie
import java.lang.reflect.Type

class MovieConverter {

    private val gson = Gson()
    private val type: Type = object : TypeToken<Movie>() {}.type

    @TypeConverter
    fun stringToMovie(json : String) : Movie {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun movieToString(movie: Movie) : String{
        return gson.toJson(movie, type)
    }
}