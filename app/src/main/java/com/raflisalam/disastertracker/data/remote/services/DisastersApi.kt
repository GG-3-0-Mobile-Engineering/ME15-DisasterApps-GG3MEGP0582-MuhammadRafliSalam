package com.raflisalam.disastertracker.data.remote.services

import com.raflisalam.disastertracker.data.remote.model.DisastersReportResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DisastersApi {

    @GET("reports")
    suspend fun getDisasterReports(
        @Query("admin") regionName: String? = null,
        @Query("disaster") disaster: String? = null,
        @Query("timeperiod") time : Number = 604800
    ): Response<DisastersReportResponse>

}