package com.raflisalam.disastertracker.common.utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SettingsPreferencesTest {

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    private lateinit var settingsPreferences: SettingsPreferences

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        settingsPreferences = SettingsPreferences()
    }

    @Test
    fun `when fun setThemeMode get edited, should store theme mode in SharedPreferences`() {
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(mockSharedPreferences.edit()).thenReturn(editor)

        settingsPreferences.setThemeMode(mockSharedPreferences, AppCompatDelegate.MODE_NIGHT_YES)

        verify(editor).putInt(SettingsPreferences.KEY_THEME, AppCompatDelegate.MODE_NIGHT_YES)
        verify(editor).apply()
    }

    @Test
    fun `when applyTheme is called, it should set the default night mode based on saved value`() {
        `when`(mockSharedPreferences.getInt(SettingsPreferences.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO))
            .thenReturn(AppCompatDelegate.MODE_NIGHT_YES)

        mockkStatic(AppCompatDelegate::class)
        every { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) } just Runs
        settingsPreferences.applyTheme(mockSharedPreferences)

        verify(mockSharedPreferences).getInt(SettingsPreferences.Companion.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO)
        verify(exactly = 1) { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
    }
}