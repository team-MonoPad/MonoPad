package com.project.monopad.di

import androidx.room.Room
import com.project.monopad.network.local.LocalDataSource
import com.project.monopad.network.local.database.DiaryDatabase
import com.project.monopad.network.local.datasource.ReviewLocalDataSource
import com.project.monopad.network.local.datasource.ReviewLocalDataSourceImpl
import org.koin.dsl.module

val localDataSourceModule = module {
    single {
        Room.databaseBuilder(get(), DiaryDatabase::class.java, "movie_diary.db")
            .fallbackToDestructiveMigration().build()
    }
    single { get<DiaryDatabase>().diaryDao() }
    single { LocalDataSource(get()) }
    single<ReviewLocalDataSource> { ReviewLocalDataSourceImpl(get()) }
}