package com.raflisalam.disastertracker.domain.model

data class WeatherReports (
    val tempC: Long,
    val clouds: Long,
    val wind: Double,
    val humidity: Long
)