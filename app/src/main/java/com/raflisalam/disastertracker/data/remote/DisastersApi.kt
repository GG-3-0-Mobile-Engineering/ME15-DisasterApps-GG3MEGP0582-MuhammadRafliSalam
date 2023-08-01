package com.raflisalam.disastertracker.data.remote

import com.raflisalam.disastertracker.data.remote.dto.DisastersReportResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DisastersApi {

    @GET("reports")
    suspend fun getDisasterReports(
        @Query("timeperiod") time : Number = 604800
    ): Response<DisastersReportResponse>
}