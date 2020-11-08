package com.project.monopad.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.monopad.data.model.dto.Genre
import java.lang.reflect.Type

class GenreConverter {

    private val gson = Gson()
    private val type: Type = object : TypeToken<List<Genre?>?>() {}.type

    @TypeConverter
    fun stringToGenre(json : String) : List<Genre> {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun genreToString(genre: List<Genre>) : String{
        return gson.toJson(genre, type)
    }
}