package com.project.monopad.di

import com.project.monopad.network.remote.api.MovieRepoApi
import com.project.monopad.network.remote.api.TvRepoApi
import com.project.monopad.network.remote.datasource.MovieRemoteDataSource
import com.project.monopad.network.remote.datasource.MovieRemoteDataSourceImpl
import com.project.monopad.network.remote.datasource.TvRemoteDataSource
import com.project.monopad.network.remote.datasource.TvRemoteDataSourceImpl
import com.project.monopad.network.repository.MovieRepo
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.network.repository.TvRepo
import com.project.monopad.network.repository.TvRepoImpl
import com.project.monopad.ui.viewmodel.MovieViewModel
import com.project.monopad.ui.viewmodel.TvViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

var networkModule = module{
    single<MovieRepoApi>{
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieRepoApi::class.java)
    }

    single<TvRepoApi>{
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvRepoApi::class.java)
    }

    single{
        val b = OkHttpClient.Builder()  // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
        b.build()
    }
}

var remoteDataSource = module {
    single<MovieRemoteDataSource>{ MovieRemoteDataSourceImpl(get()) }
    single<TvRemoteDataSource>{ TvRemoteDataSourceImpl(get()) }
}

var localDataSource = module {
}

var repositoryModule = module {
    single { MovieRepoImpl(get()) }
    single { TvRepoImpl(get()) }
}

var viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvViewModel(get()) }
}

var monoDiModule = listOf(networkModule, remoteDataSource, repositoryModule, viewModelModule)