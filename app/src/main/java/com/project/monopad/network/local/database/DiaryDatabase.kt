package com.project.monopad.network.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.monopad.model.entity.Movie
import com.project.monopad.model.entity.Review
import com.project.monopad.model.network.dto.Genre
import com.project.monopad.network.local.converter.DateConverter
import com.project.monopad.network.local.converter.GenreConverter
import com.project.monopad.network.local.converter.MovieConverter

@Database(entities = [Review::class, Movie::class, Genre::class], version = 4, exportSchema = false)
@TypeConverters(*[GenreConverter::class, MovieConverter::class, DateConverter::class])
abstract class DiaryDatabase : RoomDatabase(){

    abstract fun diaryDao() : DiaryDao

}