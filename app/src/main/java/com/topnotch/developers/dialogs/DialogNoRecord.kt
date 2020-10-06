package com.topnotch.developers.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.topnotch.developers.databinding.DialogNoRecordBinding

class DialogNoRecord(query: String) : DialogFragment() {
    private lateinit var binding: DialogNoRecordBinding
    private var query = query

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DialogNoRecordBinding.inflate(layoutInflater)
        val view = binding.root
        binding.tvTitle.text = String.format("%s %s", query, "not found!")
        binding.btnOk.setOnClickListener {
            dismiss()
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}