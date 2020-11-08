package com.project.monopad.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.monopad.data.model.entity.Movie
import com.project.monopad.data.model.entity.Review
import com.project.monopad.data.model.dto.Genre
import com.project.monopad.data.local.converter.DateConverter
import com.project.monopad.data.local.converter.GenreConverter
import com.project.monopad.data.local.converter.MovieConverter

@Database(entities = [Review::class, Movie::class, Genre::class], version = 6, exportSchema = false)
@TypeConverters(*[GenreConverter::class, MovieConverter::class, DateConverter::class])
abstract class DiaryDatabase : RoomDatabase(){

    abstract fun diaryDao() : DiaryDao

}