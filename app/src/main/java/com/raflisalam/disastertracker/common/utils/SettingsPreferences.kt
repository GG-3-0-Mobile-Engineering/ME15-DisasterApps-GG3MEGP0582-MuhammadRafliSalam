package com.raflisalam.disastertracker.common.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

class SettingsPreferences {

    fun setQueryRegionCode(sharedPref: SharedPreferences, regionCode: String) {
        val editor = sharedPref.edit()
        editor.putString(REGION_QUERY_KEY, regionCode)
        editor.apply()
    }

  /*  fun insertQueryRegionCode (sharedPref: SharedPreferences) {
        val currentRegionCode = sharedPref.getString(REGION_QUERY_KEY, Context.MODE_PRIVATE )
    }
*/
    companion object {
        private const val REGION_QUERY_KEY = "region_code"
    }
}