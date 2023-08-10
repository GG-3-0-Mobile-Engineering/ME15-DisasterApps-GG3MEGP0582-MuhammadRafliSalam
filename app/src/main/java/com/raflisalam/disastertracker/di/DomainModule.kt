package com.raflisalam.disastertracker.di

import com.raflisalam.disastertracker.common.utils.NotificationUtils
import com.raflisalam.disastertracker.domain.repository.DisastersRepository
import com.raflisalam.disastertracker.domain.repository.WeatherRepository
import com.raflisalam.disastertracker.domain.usecase.disaster.GetDisasterReportsUseCase
import com.raflisalam.disastertracker.domain.usecase.disaster.GetDisasterReportsUseCaseImpl
import com.raflisalam.disastertracker.domain.usecase.weather.GetWeatherReportsUseCase
import com.raflisalam.disastertracker.domain.usecase.weather.GetWeatherReportsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideGetDisasterReportsUseCase(
        repository: DisastersRepository,
        notificationUtils: NotificationUtils
    ): GetDisasterReportsUseCase {
        return GetDisasterReportsUseCaseImpl(repository, notificationUtils)
    }

    @Provides
    fun provideGetWeatherReportsUseCase(
        repository: WeatherRepository
    ): GetWeatherReportsUseCase {
        return GetWeatherReportsUseCaseImpl(repository)
    }
}