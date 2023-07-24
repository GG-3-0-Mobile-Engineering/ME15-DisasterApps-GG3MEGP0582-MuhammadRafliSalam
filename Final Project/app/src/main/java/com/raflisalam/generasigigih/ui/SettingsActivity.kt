package com.raflisalam.generasigigih.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.raflisalam.generasigigih.R
import com.raflisalam.generasigigih.databinding.ActivitySettingsBinding
import com.raflisalam.generasigigih.utils.Utils

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        utils = Utils()

        setupToolbar()
        setupButton()
    }

    private fun setupToolbar() {
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = "Settings"
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
                setupTheme(themeMode)
                recreate()
            }
        }
    }

    private fun setupTheme(themeMode: Int) {
        sharedPref = getSharedPreferences(KEY_THEME, MODE_PRIVATE)
        utils.applyTheme(sharedPref)
        utils.setThemeMode(sharedPref, themeMode)
    }

    companion object {
        private const val KEY_THEME = "theme_mode"
    }
}