package com.example.movieappsample.base

import com.project.monopad.haeseong.model.api.LatestMovieApi
import com.project.monopad.haeseong.model.response.LatestMovieResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {

    // 서버 주소
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "dd3529cb48a78d9d2e775be63596398a"

//    val latestMovieApi by lazy { retrofit.create<LatestMovieApi>(this) }
    val latestMovieApi by lazy { retrofit.create(LatestMovieApi::class.java) }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)

            .build()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.request()
                    .newBuilder()
                    .build()
                    .let { request ->
                        chain.proceed(request)
                    }
            }
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .build()
    }
}