package com.project.monopad.di

import com.project.monopad.data.remote.datasource.MovieRemoteDataSource
import com.project.monopad.data.remote.datasource.MovieRemoteDataSourceImpl
import com.project.monopad.data.remote.datasource.UserRemoteDataSource
import com.project.monopad.data.remote.datasource.UserRemoteDataSourceImpl
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single<MovieRemoteDataSource>{ MovieRemoteDataSourceImpl(get()) }
    single<UserRemoteDataSource>{ UserRemoteDataSourceImpl(get()) }
}