package com.project.monopad.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlin.reflect.KClass

inline fun Context.intentActionWithBundle(kClass: KClass<out Activity>, extras: Bundle.() -> Unit = {}) {
    startActivity(
        Intent(this, kClass.java).putExtras(Bundle().apply(extras))
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

fun Fragment.navigateToActivity(Activity_Id : Int){
    findNavController().navigate(Activity_Id)
}
