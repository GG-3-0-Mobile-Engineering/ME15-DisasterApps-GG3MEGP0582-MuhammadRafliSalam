package com.raflisalam.disastertracker.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.raflisalam.disastertracker.R
import com.raflisalam.disastertracker.common.utils.SettingsPreferences
import com.raflisalam.disastertracker.databinding.ActivitySettingsBinding
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    @Inject lateinit var settingsPreferences: SettingsPreferences
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsPreferences = SettingsPreferences()

        setupToolbar()
        setupButton()
    }

    private fun setupToolbar() {
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = getString(R.string.title_toolbar_settings)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setupButton() {
        binding.apply {
            btnSwitchTheme.thumbDrawable = ContextCompat.getDrawable(this@SettingsActivity, R.drawable.custom_thumb)
            btnSwitchTheme.trackDrawable = ContextCompat.getDrawable(this@SettingsActivity, R.drawable.custom_track)
            btnSwitchTheme.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            btnSwitchTheme.setOnCheckedChangeListener { compoundButton, isChecked ->
                val themeMode = if (isChecked) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                switchTheme(themeMode)
                recreate()
            }
        }
    }

    private fun switchTheme(themeMode: Int) {
        sharedPref = getSharedPreferences(KEY_THEME, MODE_PRIVATE)
        settingsPreferences.applyTheme(sharedPref)
        settingsPreferences.setThemeMode(sharedPref, themeMode)
    }

    companion object {
        private const val KEY_THEME = "theme_mode"
    }
}