package com.raflisalam.disastertracker.domain.model

import com.raflisalam.disastertracker.data.remote.dto.ReportData

data class DisasterReports (
     val coordinates: Coordinates,
     val createdAt: String,
     val imageUrl: String,
     val disasterType: String,
     val tags: Tags,
     val title: String,
     val description: String
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

data class Tags(
    val regionCode: String
)