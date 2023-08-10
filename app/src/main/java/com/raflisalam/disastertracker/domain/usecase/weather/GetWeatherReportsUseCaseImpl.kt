package com.raflisalam.disastertracker.domain.usecase.weather

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.WeatherReports
import com.raflisalam.disastertracker.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherReportsUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : GetWeatherReportsUseCase {
    override suspend fun invoke(
        apiKey: String?,
        location: String?
    ): Flow<Resource<WeatherReports>> {
        return repository.fetchWeatherReports(apiKey, location)
    }
}