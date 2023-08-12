package com.raflisalam.disastertracker.disaster.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.Coordinates
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.model.ReportData
import com.raflisalam.disastertracker.domain.model.Tags
import com.raflisalam.disastertracker.domain.usecase.disaster.GetDisasterReportsUseCase
import com.raflisalam.disastertracker.presentation.viewmodel.DisasterReportsViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DisasterViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: GetDisasterReportsUseCase

    private lateinit var viewModel: DisasterReportsViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DisasterReportsViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `when fetchDisasterReports is called, it should fill LiveData with correct data`() = testScope.runBlockingTest {
        val dummyRegionCode = "ID-JK"
        val dummyDisasterType = "Flood"
        val dummyTimePeriod = 123
        val expectedData = listOf(
            DisasterReports(
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

        val flow = flowOf(Resource.Success(expectedData))

        `when`(useCase.invoke(dummyRegionCode, dummyDisasterType, dummyTimePeriod)).thenReturn(flow)
        viewModel.fetchDisasterReports(dummyRegionCode, dummyDisasterType, dummyTimePeriod)

        val liveDataValue = viewModel.disasterReports.value
        assertTrue(liveDataValue is Resource.Success)
        assertEquals(expectedData, liveDataValue?.data)
    }

    @Test
    fun `when fetchDisasterReports encounters an exception, it should fill LiveData with Resource Error`() = testScope.runBlockingTest {
        val dummyRegionCode = "ID-JK"
        val dummyDisasterType = "Flood"
        val dummyTimePeriod = 123
        val expectedErrorMessage = "Failed to fetch disaster reports"
        val exception = Exception("An error occurred")
        val flow = flow<Resource<List<DisasterReports>>> {
            throw exception
        }

        `when`(useCase.invoke(dummyRegionCode, dummyDisasterType, dummyTimePeriod)).thenReturn(flow)
        viewModel.fetchDisasterReports(dummyRegionCode, dummyDisasterType, dummyTimePeriod)

        val liveDataValue = viewModel.disasterReports.value
        assertTrue(liveDataValue is Resource.Error)
        assertEquals(expectedErrorMessage, (liveDataValue as Resource.Error).message)
    }

    @Test
    fun `when pushNotificationFloodDepth is called, it should call checkAndSendPushNotification in use case`() {
        val reportType = "flood"
        val floodDepth = 20

        viewModel.pushNotificationFloodDepth(reportType, floodDepth)
        verify(useCase).checkAndSendPushNotification(reportType, floodDepth)
    }


}