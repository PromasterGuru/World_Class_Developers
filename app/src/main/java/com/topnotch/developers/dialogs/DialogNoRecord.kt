package com.topnotch.developers.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.topnotch.developers.databinding.DialogNoRecordBinding
import com.topnotch.developers.interfaces.IOnRecordNotFound

class DialogNoRecord(private val onRecordNotFoiund: IOnRecordNotFound, private val query: String) : DialogFragment() {
    private lateinit var binding: DialogNoRecordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNoRecordBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.tvTitle.text = String.format(
            "%s %s",
            if (query.contains(":")) query.substringAfter(":") else query,
            "not found!"
        )
        binding.btnOk.setOnClickListener {
            onRecordNotFoiund.onRecordNotFound(true)
            dismiss()
        }
        return view
    }
}