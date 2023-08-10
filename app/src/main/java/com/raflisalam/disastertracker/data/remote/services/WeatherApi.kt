package com.raflisalam.disastertracker.data.remote.services

import com.raflisalam.disastertracker.data.remote.model.WeatherReportsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getWeatherReports(
        @Query("key") apiKey: String?,
        @Query("q") location: String?
    ): Response<WeatherReportsResponse>
}