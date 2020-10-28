package com.project.monopad.di

import com.project.monopad.network.remote.api.MovieRepoApi
import com.project.monopad.network.remote.api.UserApiClient
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module{
    single<MovieRepoApi>{
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
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