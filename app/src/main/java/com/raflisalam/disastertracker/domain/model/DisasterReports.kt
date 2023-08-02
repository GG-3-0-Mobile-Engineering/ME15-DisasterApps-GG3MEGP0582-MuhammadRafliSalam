package com.raflisalam.disastertracker.domain.model


data class DisasterReports (
     val coordinates: Coordinates,
     val createdAt: String,
     val imageUrl: String,
     val disasterType: String,
     val reportData: ReportData,
     val tags: Tags,
     val title: String,
     val description: String
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

data class ReportData(
    val reportType: String,
    val floodDepth: Int
)

data class Tags(
    val regionCode: String
)