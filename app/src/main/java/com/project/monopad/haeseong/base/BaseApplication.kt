package com.example.movieappsample.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*


open class BaseApplication : Application() {
    companion object {
        private lateinit var baseApplication: BaseApplication
        var sSharedPreferences: SharedPreferences? = null

        //날짜 형식
        var DATE_FORMAT: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

        // SharedPreferences 키 값
        var TAG = "TEMPLATE_APP"

        // JWT Token 값
        var X_ACCESS_TOKEN = "X-ACCESS-TOKEN"
    }

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        if (sSharedPreferences == null) {
            sSharedPreferences = getApplicationContext().getSharedPreferences(
                TAG, Context.MODE_PRIVATE);

        }
    }


}