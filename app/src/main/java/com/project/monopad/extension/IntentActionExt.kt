package com.project.monopad.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlin.reflect.KClass

fun Context.intentActionWithBundle(kClass: KClass<out Activity>, extras: Bundle.() -> Unit = {}) {
    startActivity(
        Intent(this, kClass.java).putExtras(Bundle().apply(extras))
    )
}

fun Context.intentActionWithBundleSingleTop(kClass: KClass<out Activity>, extras: Bundle.() -> Unit = {}) {
    startActivity(
        Intent(this, kClass.java).putExtras(Bundle().apply(extras)).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    )
}

fun Fragment.intentAction(kClass: KClass<out Activity>) {
    startActivity(
        Intent(activity, kClass.java)
    )
}

fun Fragment.intentActionToUrl(url:String){
    startActivity(
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    )
}

fun AppCompatActivity.replace(@IdRes frameId: Int, fragment: androidx.fragment.app.Fragment) {
    supportFragmentManager.beginTransaction().replace(frameId, fragment, null).commit()
}