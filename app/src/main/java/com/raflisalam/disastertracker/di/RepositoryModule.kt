package com.raflisalam.disastertracker.di

import com.raflisalam.disastertracker.data.remote.DisastersApi
import com.raflisalam.disastertracker.data.repository.DisasterRepositoryImpl
import com.raflisalam.disastertracker.domain.repository.DisastersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDisasterRepository(api: DisastersApi): DisastersRepository {
        return DisasterRepositoryImpl(api)
    }
}