package com.raflisalam.disastertracker.domain.repository

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.WeatherReports
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun fetchWeatherReports(apiKey: String?, location: String?): Flow<Resource<WeatherReports>>
}