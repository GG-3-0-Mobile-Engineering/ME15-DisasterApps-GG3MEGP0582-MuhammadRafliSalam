package com.raflisalam.disastertracker.common.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject
import javax.inject.Singleton

class SettingsPreferences @Inject constructor(){
    fun setThemeMode(sharedPref: SharedPreferences, themeMode: Int) {
        val editor = sharedPref.edit()
        editor.putInt(KEY_THEME, themeMode)
        editor.apply()
    }

    fun applyTheme(sharedPref: SharedPreferences) {
        val currentThemeMode = sharedPref.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(currentThemeMode)
    }

    companion object {
        const val KEY_THEME = "theme_mode"
    }
}