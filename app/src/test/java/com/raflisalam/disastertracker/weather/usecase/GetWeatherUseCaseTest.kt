package com.raflisalam.disastertracker.weather.usecase

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.WeatherReports
import com.raflisalam.disastertracker.domain.repository.WeatherRepository
import com.raflisalam.disastertracker.domain.usecase.weather.GetWeatherReportsUseCaseImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetWeatherUseCaseTest {

    private lateinit var mockRepository: WeatherRepository
    private lateinit var useCase: GetWeatherReportsUseCaseImpl

    @Before
    fun setup() {
        mockRepository = mock(WeatherRepository::class.java)
        useCase = GetWeatherReportsUseCaseImpl(mockRepository)
    }

    @Test
    fun `use getWeatherUseCase, when use case invoke is success, invoke should return success Resource`() = runBlocking {
        val dummyApiKey = "A21sdDSADdsdas1ll"
        val dummyLocation = "Makassar"
        val expectedWeatherReports = WeatherReports(
            tempC = 12,
            clouds = 34,
            wind = 5.6,
            humidity = 78
        )
        val successResource = Resource.Success(expectedWeatherReports)

        `when`(mockRepository.fetchWeatherReports(dummyApiKey, dummyLocation)).thenReturn(flowOf(successResource))

        val result = useCase.invoke(dummyApiKey, dummyLocation)
        result.collect { resource ->
            if (resource is Resource.Success) {
                assertEquals(expectedWeatherReports, resource.data)
            }
        }
    }

    @Test
    fun `use getWeatherUseCase, when use case invoke is error, invoke should return error Resource`() = runBlocking {
        val dummyApiKey = "A21sdDSADdsdas1ll"
        val dummyLocation = "Makassar"
        val errorMessage = "API request failed"
        val errorResource: Resource<WeatherReports> = Resource.Error(errorMessage)

        `when`(mockRepository.fetchWeatherReports(dummyApiKey, dummyLocation)).thenReturn(flowOf(errorResource))

        val result = useCase.invoke(dummyApiKey, dummyLocation)
        result.collect { resource ->
            if (resource is Resource.Error) {
                assertEquals("API request failed", resource.message)
            }
        }
    }
}