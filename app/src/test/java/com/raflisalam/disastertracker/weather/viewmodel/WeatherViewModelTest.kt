package com.raflisalam.disastertracker.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.WeatherReports
import com.raflisalam.disastertracker.domain.usecase.weather.GetWeatherReportsUseCase
import com.raflisalam.disastertracker.presentation.viewmodel.WeatherReportsViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: GetWeatherReportsUseCase

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var viewModel: WeatherReportsViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherReportsViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `when fetchWeatherReports is called, it should fill LiveData with correct data`() = testScope.runBlockingTest {
        val dummyApiKey = "ksadkaKKSAKDAK213131"
        val dummyLocation = "Makassar"
        val expectedData = WeatherReports(
            tempC = 12,
            clouds = 34,
            wind = 5.6,
            humidity = 78
        )
        val flow = flowOf(Resource.Success(expectedData))

        `when`(useCase.invoke(dummyApiKey, dummyLocation)).thenReturn(flow)
        viewModel.fetchWeatherReports(dummyApiKey, dummyLocation)

        val liveDataValue = viewModel.weatherReports.value
        assertTrue(liveDataValue is Resource.Success)
        assertEquals(expectedData, liveDataValue?.data)
    }

    @Test
    fun `when fetchWeatherReports encounters an exception, it should fill LiveData with Resource Error`() = testScope.runBlockingTest {
        val dummyApiKey = "ksadkaKKSAKDAK213131"
        val dummyLocation = "Makassar"
        val expectedErrorMessage = "Failed to fetch weather reports"
        val exception = Exception("An error occurred")
        val flow = flow<Resource<WeatherReports>> {
            throw exception
        }

        `when`(useCase.invoke(dummyApiKey, dummyLocation)).thenReturn(flow)
        viewModel.fetchWeatherReports(dummyApiKey, dummyLocation)

        val liveDataValue = viewModel.weatherReports.value
        assertTrue(liveDataValue is Resource.Error)
        assertEquals(expectedErrorMessage, (liveDataValue as Resource.Error).message)
    }


}