package com.raflisalam.disastertracker.common.helper

import com.raflisalam.disastertracker.data.remote.model.DisastersReportResponse
import com.raflisalam.disastertracker.data.remote.model.WeatherReportsResponse
import com.raflisalam.disastertracker.domain.model.Coordinates
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.model.ReportData
import com.raflisalam.disastertracker.domain.model.Tags
import com.raflisalam.disastertracker.domain.model.WeatherReports

fun getResponseWeatherApiToModel(response: WeatherReportsResponse?): WeatherReports {
    val data = response?.current
    return WeatherReports(
        tempC = data?.tempC ?: 0,
        clouds = data?.cloud ?: 0,
        wind = data?.windMph ?: 0.0,
        humidity = data?.humidity ?: 0
    )
}

fun getResponseDisasterToModel(response: DisastersReportResponse?): List<DisasterReports> {
    val disasterReportsList = mutableListOf<DisasterReports>()

    response?.result?.objects?.output?.geometries?.forEach { geometry ->
        val properties = geometry.properties

        val disasterReports = DisasterReports(
            coordinates = Coordinates(geometry.coordinates?.get(0) ?: 0.0, geometry.coordinates?.get(1) ?: 0.0),
            createdAt = properties?.createdAt ?: "Unknown Date",
            imageUrl = properties?.imageUrl ?: DisasterPropertiesHelper.getDisasterImage(properties?.disasterType.toString()),
            disasterType = properties?.disasterType ?: "Unknown Disaster",
            reportData = ReportData(properties?.reportData?.reportType ?: "flood", properties?.reportData?.flood_depth ?: 0),
            tags = Tags(regionCode = properties?.tags?.instanceRegionCode.toString()),
            title = properties?.title ?: DisasterPropertiesHelper.getDisasterTitle(properties?.disasterType.toString()),
            description = properties?.description ?: DisasterPropertiesHelper.getDisasterDesc(properties?.disasterType.toString())
        )

        disasterReportsList.add(disasterReports)
    }

    return disasterReportsList
}


