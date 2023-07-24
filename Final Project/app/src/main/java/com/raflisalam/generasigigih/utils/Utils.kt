package com.raflisalam.generasigigih.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate

class Utils {

    companion object {
        private const val KEY_THEME = "theme_mode"
    }

    fun hideKeyboard(activity: Activity) {
        val view: View? = activity.currentFocus
        view?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun setThemeMode(sharedPref: SharedPreferences, themeMode: Int) {
        val editor = sharedPref.edit()
        editor.putInt(KEY_THEME, themeMode)
        editor.apply()
    }

    fun applyTheme(sharedPref: SharedPreferences) {
        val currentThemeMode = sharedPref.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(currentThemeMode)
    }
}