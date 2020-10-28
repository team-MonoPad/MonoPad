package com.project.monopad.di

import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.network.repository.ReviewRepoImpl
import com.project.monopad.network.repository.UserRepoImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepoImpl(get()) }
    single { UserRepoImpl(get()) }
    single { ReviewRepoImpl(get(),get())}
}