package com.raflisalam.disastertracker

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.common.helper.getResponseApiToModelDomain
import com.raflisalam.disastertracker.data.remote.model.DisastersReportResponse
import com.raflisalam.disastertracker.data.remote.services.DisastersApi
import com.raflisalam.disastertracker.data.repository.DisasterRepositoryImpl
import com.raflisalam.disastertracker.domain.model.Coordinates
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.model.ReportData
import com.raflisalam.disastertracker.domain.model.Tags
import com.raflisalam.disastertracker.domain.repository.DisastersRepository
import io.mockk.coEvery
import io.mockk.mockk
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
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.OngoingStubbing
import retrofit2.HttpException
import retrofit2.Response


class DisasterRepositoryImplTest {

    @Mock
    private lateinit var mockApiServices: DisastersApi
    private lateinit var repo: DisastersRepository

    @Before
    fun setUp() {
        mockApiServices = mock(DisastersApi::class.java)
        repo = DisasterRepositoryImpl(mockApiServices)
    }

    @Test
    fun `when fetchDisasterReports is Success, Response Api should return Response Success`(): Unit = runBlocking {
        val regionName = "RegionName"
        val disasterType = "Disaster"
        val timePeriod = 123
        val dummyApiResponse = mock(DisastersReportResponse::class.java)

        `when`(mockApiServices.getDisasterReports(regionName, disasterType, timePeriod)).thenReturn(
            Response.success(dummyApiResponse))
    }

    @Test
    fun `when apiResponse is successful, repo should map apiResponse to model domain`() = runBlocking {
        val regionName = "RegionName"
        val disasterType = "Disaster"
        val timePeriod = 123
        val dummyApiResponse = mock(DisastersReportResponse::class.java)

        `when`(mockApiServices.getDisasterReports(regionName, disasterType, timePeriod)).thenReturn(
            Response.success(dummyApiResponse))

        val mapApiResponse = getResponseApiToModelDomain(dummyApiResponse)
        val flow = repo.fetchDisasterReports(regionName, disasterType, timePeriod)

        flow.collect { resource ->
            if (resource is Resource.Success) {
                assertEquals(mapApiResponse, resource.data)
            }
        }
    }

    @Test
    fun `when fetchDisasterReports is Error, Response Api should return Resource Error`(): Unit = runBlocking {
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
    fun testFetchDisasterReports_HttpException() = runBlocking {
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
    fun `when fetchDisasterReports encounters IOException, it should return Resource Error`() = runBlocking {
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
}
