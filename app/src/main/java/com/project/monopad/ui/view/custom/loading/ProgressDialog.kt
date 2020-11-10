package com.project.monopad.ui.view.custom.loading

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.LinearLayout
import android.view.LayoutInflater
import com.project.monopad.R


class ProgressDialog(context: Context) {

    private var view: View
    private var linear: LinearLayout
    private var builder: AlertDialog.Builder
    lateinit var dialog: Dialog

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.fragment_progress_dialog, null)
        linear = view.findViewById(R.id.linear)
        builder = AlertDialog.Builder(context)
    }

    fun show() {
        builder.setView(view)
        dialog = builder.create()?.apply {
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setCancelable(false)
            show()
        }!!
    }

    fun dismiss() {
        dialog.dismiss()
    }
}