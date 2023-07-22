package com.raflisalam.generasigigih.api

import com.raflisalam.generasigigih.DisasterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("reports")
    fun getDisasterReports(
        @Query("timeperiod") number: Number
        /*@Query("end") end: String*/
    ): Call<DisasterResponse>

    @GET("reports")
    fun getDisastersBasedOnArea(
        @Query("admin") regionCode: String,
        @Query("timeperiod") number: Number
    ): Call<DisasterResponse>

}