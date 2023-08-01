package com.raflisalam.disastertracker.common.helper

import com.raflisalam.disastertracker.data.remote.dto.DisastersReportResponse
import com.raflisalam.disastertracker.domain.model.Coordinates
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.model.Tags


fun getResponseApiToModelDomain(response: DisastersReportResponse?): List<DisasterReports> {
    val disasterReportsList = mutableListOf<DisasterReports>()

    response?.result?.objects?.output?.geometries?.forEach { geometry ->
        val properties = geometry.properties

        val disasterReports = DisasterReports(
            coordinates = Coordinates(geometry.coordinates?.get(0) ?: 0.0, geometry.coordinates?.get(1) ?: 0.0),
            createdAt = properties?.createdAt ?: "Unknown Date",
            imageUrl = properties?.imageUrl ?: DisasterPropertiesHelper.getDisasterImage(properties?.disasterType.toString()),
            disasterType = properties?.disasterType ?: "Unknown Disaster",
            tags = Tags(properties?.tags?.regionCode.toString()),
            title = properties?.title ?: DisasterPropertiesHelper.getDisasterTitle(properties?.disasterType.toString()),
            description = properties?.description ?: DisasterPropertiesHelper.getDisasterDesc(properties?.disasterType.toString())
        )

        disasterReportsList.add(disasterReports)
    }

    return disasterReportsList
}

