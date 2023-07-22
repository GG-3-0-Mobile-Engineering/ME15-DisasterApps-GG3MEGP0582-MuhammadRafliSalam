package com.raflisalam.generasigigih.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Utils {

    fun hideKeyboard(activity: Activity) {
        val view: View? = activity.currentFocus
        view?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}