package com.topnotch.developers.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.topnotch.developers.databinding.DialogInternetCheckBinding
import com.topnotch.developers.interfaces.OnInternetRetryListener

class DialogInternetCheck : DialogFragment() {
    private lateinit var binding: DialogInternetCheckBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DialogInternetCheckBinding.inflate(layoutInflater)
        val view :View = binding.root
        binding.btnRetry.setOnClickListener {
           val onInternetRetryListener: OnInternetRetryListener = activity as OnInternetRetryListener
            onInternetRetryListener.onRetry(true)
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        return view
    }
}