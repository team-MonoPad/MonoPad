package com.project.monopad.haeseong.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.project.monopad.R
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {

    private lateinit var testViewModel: TestViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testViewModel =
            ViewModelProviders.of(this).get(TestViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_test, container, false)
        testViewModel.text.observe(viewLifecycleOwner, Observer {
            test_text.text = it
        })
        return root
    }
}