package com.raflisalam.disastertracker.disaster.usecase

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.common.helper.Convert
import com.raflisalam.disastertracker.common.utils.NotificationUtils
import com.raflisalam.disastertracker.domain.model.Coordinates
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.model.ReportData
import com.raflisalam.disastertracker.domain.model.Tags
import com.raflisalam.disastertracker.domain.repository.DisastersRepository
import com.raflisalam.disastertracker.domain.usecase.disaster.GetDisasterReportsUseCaseImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class GetDisasterUseCaseTest {

    private lateinit var mockRepository: DisastersRepository
    private lateinit var mockNotificationUtils: NotificationUtils
    private lateinit var useCase: GetDisasterReportsUseCaseImpl

    @Before
    fun setup() {
        mockRepository = mock(DisastersRepository::class.java)
        mockNotificationUtils =  mock(NotificationUtils::class.java)
        useCase = GetDisasterReportsUseCaseImpl(mockRepository, mockNotificationUtils)
    }

    @Test
    fun `when fetchDisasterReports is invoke success, invoke should return success resource`() = runBlocking {
        val dummyRegion = "dki jakarta"
        val dummyRegionCode = Convert.regionNameToRegionCode(dummyRegion)
        val dummyDisasterType = "flood"
        val dummyTimePeriod = 604800

        val expectedDisasterReports = listOf(DisasterReports(
            coordinates = Coordinates(
                latitude = 12.3,
                longitude = -456.78
            ),
            createdAt = "10-20-2024T:10:30:30",
            imageUrl = "https://testing.com",
            disasterType = "flood",
            reportData = ReportData(
                reportType = "flood",
                floodDepth = 1
            ),
            tags = Tags(
                regionCode = "ID-JK"
            ),
            title = "invoke success",
            description = "invoke success should return success resource"
        ))
        val successResource = Resource.Success(expectedDisasterReports)

        `when`(mockRepository.fetchDisasterReports(dummyRegionCode, dummyDisasterType, dummyTimePeriod)).thenReturn(flowOf(successResource))

        val result: Flow<Resource<List<DisasterReports>>> = useCase.invoke(dummyRegion, dummyDisasterType, dummyTimePeriod)

        result.collect { resource ->
            if (resource is Resource.Success) {
                assertEquals(expectedDisasterReports, resource.data)
            }
        }
    }

    @Test
    fun `when the checkAndPushNotification is success get parameters , should call notificationUtils`() {
        val reportType = "flood"
        val floodDepth = 20

        useCase.checkAndSendPushNotification(reportType, floodDepth)
        verify(mockNotificationUtils).checkAndSendPushNotification(reportType, floodDepth)
    }
}