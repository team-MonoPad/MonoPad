package com.project.monopad.di

import com.project.monopad.ui.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { DiaryViewModel(get(),get()) }
    viewModel { PersonViewModel(get()) }
    viewModel { ImageSelectViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}