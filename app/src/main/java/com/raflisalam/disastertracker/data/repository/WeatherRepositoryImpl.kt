package com.raflisalam.disastertracker.data.repository

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.common.helper.getResponseWeatherApiToModel
import com.raflisalam.disastertracker.data.remote.services.WeatherApi
import com.raflisalam.disastertracker.domain.model.WeatherReports
import com.raflisalam.disastertracker.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiServices: WeatherApi
) : WeatherRepository {
    override suspend fun fetchWeatherReports(
        apiKey: String?,
        location: String?
    ): Flow<Resource<WeatherReports>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = apiServices.getWeatherReports(apiKey, location)
            if (apiResponse.isSuccessful) {
                val weatherReportsResponse = apiResponse.body()
                val weatherReports = getResponseWeatherApiToModel(weatherReportsResponse)
                emit(Resource.Success(weatherReports))
            } else {
                emit(Resource.Error("API request failed with code ${apiResponse.code()}"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}