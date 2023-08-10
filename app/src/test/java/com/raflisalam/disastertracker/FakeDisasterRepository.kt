package com.raflisalam.disastertracker

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.data.remote.model.DisastersReportResponse
import com.raflisalam.disastertracker.data.remote.services.DisastersApi
import com.raflisalam.disastertracker.domain.model.Coordinates
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.model.ReportData
import com.raflisalam.disastertracker.domain.model.Tags
import com.raflisalam.disastertracker.domain.repository.DisastersRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response


class FakeDisastersRepository : DisastersRepository {

    override suspend fun fetchDisasterReports(
        regionName: String?,
        disaster: String?,
        timePeriod: Number
    ): Flow<Resource<List<DisasterReports>>> = flowOf(
        Resource.Success(
            listOf(
                DisasterReports(
                    coordinates = Coordinates(
                        latitude = 23.0,
                        longitude = -119.990
                    ),
                    createdAt = "10nOvj32131s",
                    imageUrl = "https://testing.com",
                    disasterType = "gempaaaa",
                    reportData = ReportData(
                        reportType = "banjirrr",
                        floodDepth = 2
                    ),
                    tags = Tags(
                        regionCode = "ID-AC"
                    ),
                    title = "Ini Testing",
                    description = "Ini testinggggggggggggggggggggggg"
                ),
                DisasterReports(
                    coordinates = Coordinates(
                        latitude = 24.0,
                        longitude = -119.120
                    ),
                    createdAt = "83281djjas",
                    imageUrl = "https://testing23.com",
                    disasterType = "gg",
                    reportData = ReportData(
                        reportType = "flood",
                        floodDepth = 0
                    ),
                    tags = Tags(
                        regionCode = "ID-AB"
                    ),
                    title = "Ini Testing",
                    description = "Ini testinggggggggggggggggggggggg"
                ),
                DisasterReports(
                    coordinates = Coordinates(
                        latitude = 21.0,
                        longitude = -120.990
                    ),
                    createdAt = "askdkakd",
                    imageUrl = "https://testing.com/21asdada.jpeg",
                    disasterType = "ff",
                    reportData = ReportData(
                        reportType = "flood",
                        floodDepth = 0
                    ),
                    tags = Tags(
                        regionCode = "ID-KL"
                    ),
                    title = "Ini Testing",
                    description = "Ini testinggggggggggggggggggggggg"
                )
            )
        )
    )
}