package com.project.monopad.ui.view.custom.bottomsheetdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.monopad.R
import com.project.monopad.databinding.FragmentDiaryListBottomSheetBinding
import kotlinx.android.synthetic.main.fragment_diary_list_bottom_sheet.view.*

class DiaryListBottomSheetFragment(private val adapter : BottomSheetListAdapter) : BottomSheetDialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentDiaryListBottomSheetBinding
                = DataBindingUtil.inflate<FragmentDiaryListBottomSheetBinding>(
            inflater,
            R.layout.fragment_diary_list_bottom_sheet,
            container,
            false
        ).root

        fragmentDiaryListBottomSheetBinding.rc_bs_list_item.adapter = adapter

        return fragmentDiaryListBottomSheetBinding
    }

}