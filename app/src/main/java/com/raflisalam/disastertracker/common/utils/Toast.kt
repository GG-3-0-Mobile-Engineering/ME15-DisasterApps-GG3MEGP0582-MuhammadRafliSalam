package com.raflisalam.disastertracker.common.utils

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(message: String, isShortToast: Boolean = false) {
    if (isShortToast) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}