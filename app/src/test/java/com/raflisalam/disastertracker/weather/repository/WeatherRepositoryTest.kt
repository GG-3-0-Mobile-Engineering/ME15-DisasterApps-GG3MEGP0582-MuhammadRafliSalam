package com.raflisalam.disastertracker.weather.repository

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.common.helper.getResponseWeatherApiToModel
import com.raflisalam.disastertracker.data.remote.model.WeatherReportsResponse
import com.raflisalam.disastertracker.data.remote.services.WeatherApi
import com.raflisalam.disastertracker.data.repository.WeatherRepositoryImpl
import com.raflisalam.disastertracker.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.HttpException
import retrofit2.Response

class WeatherRepositoryTest {

    @Mock
    private lateinit var mockApiServices: WeatherApi

    private lateinit var repo: WeatherRepository

    @Before
    fun setup() {
        mockApiServices = mock(WeatherApi::class.java)
        repo = WeatherRepositoryImpl(mockApiServices)
    }

    @Test
    fun `when getWeatherReports is Success, Response Api should return response success`(): Unit = runBlocking {
        val dummyApiKey = "USADjsdja1i21jedassad211"
        val dummyLocation = "Makassar"
        val dummyApiResponse = mock(WeatherReportsResponse::class.java)

        `when`(mockApiServices.getWeatherReports(dummyApiKey, dummyLocation)).thenReturn(Response.success(dummyApiResponse))
    }

    @Test
    fun `when getWeatherReports is Successful, repo should map apiResponse to model domain and return Resource Success`(): Unit = runBlocking {
        val dummyApiKey = "USADjsdja1i21jedassad211"
        val dummyLocation = "Makassar"
        val dummyApiResponse = mock(WeatherReportsResponse::class.java)

        `when`(mockApiServices.getWeatherReports(dummyApiKey, dummyLocation)).thenReturn(Response.success(dummyApiResponse))

        val mapApiResponse = getResponseWeatherApiToModel(dummyApiResponse)

        val flow = repo.fetchWeatherReports(dummyApiKey, dummyLocation)
        flow.collect { resource ->
            if (resource is Resource.Success) {
                assertEquals(mapApiResponse, resource.data)
            }
        }
    }

    @Test
    fun `when getWeatherReports is Error, Response Api should return Resource Error`(): Unit = runBlocking {
        val dummyApiKey = "USADjsdja1i21jedassad211"
        val dummyLocation = "Makassar"
        val errorCode = 400
        val errorResponseBody = "body == null"
        val errorResponse: Response<WeatherReportsResponse> = Response.error(errorCode, errorResponseBody.toResponseBody())

        `when`(mockApiServices.getWeatherReports(dummyApiKey, dummyLocation)).thenReturn(errorResponse)

        val flow = repo.fetchWeatherReports(dummyApiKey, dummyLocation)

        flow.collect { resource ->
            if (resource is Resource.Error) {
                assertEquals("API request failed with code 400", resource.message)
            }
        }
    }

    @Test
    fun `when getWeatherReports encounters HttpException, it should return Resource Error`() = runBlocking {
        val dummyApiKey = "USADjsdja1i21jedassad211"
        val dummyLocation = "Makassar"
        val httpException = mock(HttpException::class.java)

        `when`(mockApiServices.getWeatherReports(dummyApiKey, dummyLocation)).thenThrow(httpException)
        `when`(httpException.localizedMessage).thenReturn("Server error")

        val flow = repo.fetchWeatherReports(dummyApiKey, dummyLocation)

        flow.collect { resource ->
            if (resource is Resource.Error) {
                assertEquals("Server error", resource.message)
            }
        }
    }

    @Test
    fun `when getWeatherReports encounters IOException, it should return Resource Error`() = runBlocking {
        val dummyApiKey = "USADjsdja1i21jedassad211"
        val dummyLocation = "Makassar"
        val apiServices: WeatherApi = mockk()
        val repository = WeatherRepositoryImpl(apiServices)

        val ioException = mock(IOException::class.java)
        val expectedException = "Couldn't reach server. Check your internet connection"

        coEvery { apiServices.getWeatherReports(dummyApiKey, dummyLocation) } throws IOException(ioException)

        val flow = repository.fetchWeatherReports(dummyApiKey, dummyLocation)
        flow.collect { resource ->
            if (resource is Resource.Error) {
                assertEquals(expectedException, resource.message)
            }
        }
    }
}