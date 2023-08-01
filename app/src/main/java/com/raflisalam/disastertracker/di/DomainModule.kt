package com.raflisalam.disastertracker.di

import com.raflisalam.disastertracker.domain.repository.DisastersRepository
import com.raflisalam.disastertracker.domain.usecase.GetDisasterReportsUseCase
import com.raflisalam.disastertracker.domain.usecase.GetDisasterReportsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideGetDisasterReportsUseCase(
        repository: DisastersRepository
    ): GetDisasterReportsUseCase {
        return GetDisasterReportsUseCaseImpl(repository)
    }
}