package com.example.movieappsample.base


import com.example.movieappsample.base.BaseApplication.Companion.X_ACCESS_TOKEN
import com.example.movieappsample.base.BaseApplication.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * 네트워크 API 통신을 할 때 헤더에 JWT Token 을 세팅한다.
 * JWT 는 SharedPreferences 에 X_ACCESS_TOKEN 이라는 키값으로 저장한다.
 */
class XAccessTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences?.getString(X_ACCESS_TOKEN, "3e408a671cb115c130287126045c1c71")
        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}