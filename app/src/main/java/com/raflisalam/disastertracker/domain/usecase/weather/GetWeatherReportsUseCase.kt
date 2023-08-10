package com.raflisalam.disastertracker.domain.usecase.weather

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.WeatherReports
import kotlinx.coroutines.flow.Flow


interface GetWeatherReportsUseCase {
    suspend operator fun invoke(apiKey: String?, location: String?): Flow<Resource<WeatherReports>>
}