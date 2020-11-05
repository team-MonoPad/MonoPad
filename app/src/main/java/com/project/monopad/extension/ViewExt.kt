package com.project.monopad.extension

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.project.monopad.ui.view.error.ErrorActivity

fun showSnack(view: View, msg : String){
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("RETRY",null).show()
}

fun showToast(context : Context, msg:String){
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}
