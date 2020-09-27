package com.project.monopad

import android.os.Bundle
import android.view.Menu
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ScrollingActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarLayout: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        init()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        toolbarLayout = findViewById(R.id.toolbar_layout)
    }

}