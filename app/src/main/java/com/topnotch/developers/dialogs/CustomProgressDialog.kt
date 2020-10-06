package com.topnotch.developers.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.res.ResourcesCompat
import com.topnotch.developers.R
import com.topnotch.developers.databinding.CustomProgressDialogBinding

class CustomProgressDialog {

    lateinit var dialog: CustomDialog
    private lateinit var binding:CustomProgressDialogBinding

    fun show(context: Context): Dialog {
        return show(context, "", "")
    }

    fun show(context: Context, title: String, sub_title: String): Dialog {
        val inflater = (context as Activity).layoutInflater
        binding = CustomProgressDialogBinding.inflate(inflater)
        binding.cpTitle.text = title
        binding.cpSubTitle.text = sub_title

        // Progress Bar Color
        setColorFilter(binding.cpPbar.indeterminateDrawable, ResourcesCompat.getColor(context.resources, R.color.colorPrimary, null))

        dialog = CustomDialog(context)
        dialog.setContentView(binding.root)
        dialog.show()
        return dialog
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            // Set Semi-Transparent Color for Dialog Background
            window?.decorView?.rootView?.setBackgroundResource(R.color.dialogBackground)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                    insets.consumeSystemWindowInsets()
                }
            }
        }
    }
}
