package com.topnotch.developers.view

import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.topnotch.developers.R
import retrofit2.HttpException

/**
 * Created by promasterguru on 17/10/2021.
 */
open class BaseActivity : AppCompatActivity() {
    private var _progressBar: ProgressBar? = null
    val progressBar get() = _progressBar!!
    fun showToast(msg: String, long: Boolean? = false) {
        Toast.makeText(this, msg, if (long == true) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
            .show()
    }

    fun showDialogFragment(dialogFragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.add(android.R.id.content, dialogFragment, tag).addToBackStack(null)
            .commit()
    }

    fun errorHandler(error: Any) {
        try {
            when (error) {
                is Throwable -> showToast(getString(R.string.server_error))
                is HttpException -> showToast(getString(R.string.not_found_try_again))
                else -> showToast(getString(R.string.erro_while_loading))
            }
            throw error as Throwable
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun initProgressBar() {
        _progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        _progressBar!!.isIndeterminate = true
        val relativeLayout = RelativeLayout(this)
        relativeLayout.gravity = Gravity.CENTER
        relativeLayout.addView(_progressBar)

        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        View.INVISIBLE.also { _progressBar!!.visibility = it }
        addContentView(relativeLayout, params)
    }
}