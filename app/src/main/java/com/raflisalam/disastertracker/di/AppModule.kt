package com.raflisalam.disastertracker.di

import com.raflisalam.disastertracker.common.utils.SettingsPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideSettingsPref(): SettingsPreferences {
        return SettingsPreferences()
    }

}