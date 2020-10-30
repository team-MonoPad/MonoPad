package com.project.monopad.di

import com.project.monopad.network.remote.api.MovieRepoApi
import com.project.monopad.network.remote.api.UserApiClient
import com.project.monopad.exception.NoConnectionInterceptor
import okhttp3.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module{
    single<MovieRepoApi>{
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get(named("network")))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieRepoApi::class.java)
    }

    single(named("network")){
        OkHttpClient.Builder().apply {
            addInterceptor(NoConnectionInterceptor(get()))
                .connectTimeout(1,TimeUnit.MINUTES)
                .readTimeout(30,TimeUnit.SECONDS)
        }.build()
    }

    single{
        UserApiClient
    }
}