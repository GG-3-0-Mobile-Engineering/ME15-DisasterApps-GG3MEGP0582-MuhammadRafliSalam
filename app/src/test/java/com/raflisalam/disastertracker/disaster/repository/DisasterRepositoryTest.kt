package com.raflisalam.disastertracker.disaster.repository

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.common.helper.getResponseDisasterToModel
import com.raflisalam.disastertracker.data.remote.model.DisastersReportResponse
import com.raflisalam.disastertracker.data.remote.services.DisastersApi
import com.raflisalam.disastertracker.data.repository.DisasterRepositoryImpl
import com.raflisalam.disastertracker.domain.repository.DisastersRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.HttpException
import retrofit2.Response


class DisasterRepositoryTest {

    @Mock
    private lateinit var mockApiServices: DisastersApi
    private lateinit var repo: DisastersRepository

    @Before
    fun setUp() {
        mockApiServices = mock(DisastersApi::class.java)
        repo = DisasterRepositoryImpl(mockApiServices)
    }

    @Test
    fun `when getDisasterReports is Success, Response Api should return Response Success`(): Unit = runBlocking {
        val regionName = "RegionName"
        val disasterType = "Disaster"
        val timePeriod = 123
        val dummyApiResponse = mock(DisastersReportResponse::class.java)

        `when`(mockApiServices.getDisasterReports(regionName, disasterType, timePeriod)).thenReturn(
            Response.success(dummyApiResponse))
    }

    @Test
    fun `when getDisasterReports is successful, repo should map apiResponse to model domain and return Resource Success`() = runBlocking {
        val regionName = "RegionName"
        val disasterType = "Disaster"
        val timePeriod = 123
        val dummyApiResponse = mock(DisastersReportResponse::class.java)

        `when`(mockApiServices.getDisasterReports(regionName, disasterType, timePeriod)).thenReturn(
            Response.success(dummyApiResponse))

        val mapApiResponse = getResponseDisasterToModel(dummyApiResponse)
        val flow = repo.fetchDisasterReports(regionName, disasterType, timePeriod)

        flow.collect { resource ->
            if (resource is Resource.Success) {
                assertEquals(mapApiResponse, resource.data)
            }
        }
    }

    @Test
    fun `when getDisasterReports is Error, Response Api should return Resource Error`(): Unit = runBlocking {
        val regionName = "RegionName"
        val disaster = "Disaster"
        val timePeriod = 123
        val errorCode = 400
        val errorResponseBody = "body == null"
        val errorResponse: Response<DisastersReportResponse> = Response.error(errorCode, errorResponseBody.toResponseBody())

        `when`(mockApiServices.getDisasterReports(regionName, disaster, timePeriod)).thenReturn(errorResponse)

        val flow = repo.fetchDisasterReports(regionName, disaster, timePeriod)

        flow.collect { resource ->
            if (resource is Resource.Error) {
                assertEquals("API request failed with code 400", resource.message)
            }
        }
    }

    @Test
    fun `when getDisasterReports encounters HttpException, it should return Resource Error`() = runBlocking {
        val regionName = "RegionName"
        val disaster = "Disaster"
        val timePeriod = 123
        val httpException = mock(HttpException::class.java)

        `when`(mockApiServices.getDisasterReports(regionName, disaster, timePeriod)).thenThrow(httpException)
        `when`(httpException.localizedMessage).thenReturn("Server error")

        val flow = repo.fetchDisasterReports(regionName, disaster, timePeriod)

        flow.collect { resource ->
            if (resource is Resource.Error) {
                assertEquals("Server error", resource.message)
            }
        }
    }

    @Test
    fun `when getDisasterReports encounters IOException, it should return Resource Error`() = runBlocking {
        val regionName = "RegionName"
        val disasterType = "Disaster"
        val timePeriod = 123
        val apiServices: DisastersApi = mockk()
        val repository = DisasterRepositoryImpl(apiServices)

        val ioException = mock(IOException::class.java)
        val expectedException = "Couldn't reach server. Check your internet connection"

        coEvery { apiServices.getDisasterReports(regionName, disasterType, timePeriod) } throws IOException(ioException)

        val flow = repository.fetchDisasterReports(regionName, disasterType, timePeriod)
        flow.collect { resource ->
            if (resource is Resource.Error) {
                assertEquals(expectedException, resource.message)
            }
        }
    }

    @Test
    fun `when query regionCode and disasterType is null, call api with null regionCode and disasterType argument instead`(): Unit = runBlocking {
        val dummyPeriod = 604800
        repo.fetchDisasterReports(null, null, dummyPeriod).collect()
        Mockito.verify(mockApiServices).getDisasterReports(null, null, dummyPeriod)
    }
}
