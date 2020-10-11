package com.project.monopad.di

import androidx.room.Room
import com.project.monopad.network.local.LocalDataSource
import com.project.monopad.network.local.database.DiaryDatabase
import com.project.monopad.network.local.datasource.ReviewLocalDataSource
import com.project.monopad.network.local.datasource.ReviewLocalDataSourceImpl
import com.project.monopad.network.remote.api.MovieRepoApi
import com.project.monopad.network.remote.datasource.MovieRemoteDataSource
import com.project.monopad.network.remote.datasource.MovieRemoteDataSourceImpl
import com.project.monopad.network.repository.MovieRepoImpl
import com.project.monopad.ui.viewmodel.MovieViewModel
import com.project.monopad.network.remote.api.UserApiClient
import com.project.monopad.network.remote.datasource.*
import com.project.monopad.network.repository.*
import com.project.monopad.ui.viewmodel.*
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

    single{
        val b = OkHttpClient.Builder()  // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
        b.build()
    }

    single{
        UserApiClient
    }
}

var remoteDataSourceModule = module {
    single<MovieRemoteDataSource>{ MovieRemoteDataSourceImpl(get()) }
    single<UserRemoteDataSource>{ UserRemoteDataSourceImpl(get()) }
}

var localDataSourceModule = module {
    single {
        Room.databaseBuilder(get(), DiaryDatabase::class.java, "movie_diary.db")
            .fallbackToDestructiveMigration().build()
    }
    single { get<DiaryDatabase>().diaryDao() }
    single { LocalDataSource(get()) }
    single<ReviewLocalDataSource> { ReviewLocalDataSourceImpl(get()) }
}

var repositoryModule = module {
    single { MovieRepoImpl(get()) }
    single { UserRepoImpl(get()) }
    single { ReviewRepoImpl(get()) }
}

var viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { DetailViewModel(get())}
    viewModel { DiaryViewModel(get())}
    viewModel { VideoViewModel(get())}
}

var monoDiModule = listOf(networkModule, remoteDataSourceModule, localDataSourceModule, repositoryModule, viewModelModule)