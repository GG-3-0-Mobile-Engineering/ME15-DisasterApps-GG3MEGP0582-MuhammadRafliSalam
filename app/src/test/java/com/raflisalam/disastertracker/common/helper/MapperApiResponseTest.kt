package com.raflisalam.disastertracker.common.helper

import com.raflisalam.disastertracker.common.helper.DisasterPropertiesHelper
import com.raflisalam.disastertracker.common.helper.getResponseDisasterToModel
import com.raflisalam.disastertracker.common.helper.getResponseWeatherApiToModel
import com.raflisalam.disastertracker.data.remote.model.Current
import com.raflisalam.disastertracker.data.remote.model.DisastersReportResponse
import com.raflisalam.disastertracker.data.remote.model.Geometry
import com.raflisalam.disastertracker.data.remote.model.Objects
import com.raflisalam.disastertracker.data.remote.model.Output
import com.raflisalam.disastertracker.data.remote.model.Properties
import com.raflisalam.disastertracker.data.remote.model.Result
import com.raflisalam.disastertracker.data.remote.model.WeatherReportsResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MapperApiResponseTest {

    @Test
    fun `use getResponseDisasterToModel from MapperApiResponse Helper, when getResponseDisasterToModel get response is not null, should return the data`() {

        val fakeResponse = mockk<DisastersReportResponse>()
        val fakeGeometries = mockk<Geometry>()
        val fakeProperties = mockk<Properties>()
        val fakeResult = mockk<Result>()
        val fakeObjects = mockk<Objects>()
        val fakeOutput = mockk<Output>()

        every { fakeGeometries.properties } returns fakeProperties
        every { fakeGeometries.coordinates } returns listOf(1.23, 4.56)
        every { fakeProperties.createdAt } returns "2040-04-10T12:34:56"
        every { fakeProperties.imageUrl } returns "http://fakeImage.com/fake.jpg/"
        every { fakeProperties.disasterType } returns "flood"
        every { fakeProperties.reportData?.reportType } returns "flood on jakarta"
        every { fakeProperties.reportData?.flood_depth } returns 20
        every { fakeProperties.tags?.regionCode } returns "ID-ID"
        every { fakeProperties.title } returns "Flood with a depth of 20 meters occurred in Jakarta"
        every { fakeProperties.description } returns "This flood occurred because Jakarta residents continued to build buildings instead of plants"

        every { fakeResponse.result } returns fakeResult
        every { fakeResult.objects } returns fakeObjects
        every { fakeObjects.output } returns fakeOutput
        every { fakeOutput.geometries } returns listOf(fakeGeometries)

        val response = getResponseDisasterToModel(fakeResponse)

        assertEquals(1, response.size)
        val actualResponse = response[0]
        assertEquals(1.23, actualResponse.coordinates.latitude, 0.001)
        assertEquals(4.56, actualResponse.coordinates.longitude, 0.001)
        assertEquals("2040-04-10T12:34:56", actualResponse.createdAt)
        assertEquals("http://fakeImage.com/fake.jpg/", actualResponse.imageUrl)
        assertEquals("flood", actualResponse.disasterType)
        assertEquals("flood on jakarta", actualResponse.reportData.reportType)
        assertEquals(20, actualResponse.reportData.floodDepth)
        assertEquals("ID-ID", actualResponse.tags.regionCode)
        assertEquals("Flood with a depth of 20 meters occurred in Jakarta", actualResponse.title)
        assertEquals("This flood occurred because Jakarta residents continued to build buildings instead of plants", actualResponse.description)
    }

    /*@Test
    fun `when getResponseDisasterToModel is success get response but object imageUrl, title, and desc the data is null, should return default data from DisasterPropertiesHelper`() {
        val fakeResponse = mockk<DisastersReportResponse>()
        val fakeGeometries = mockk<Geometry>()
        val fakeProperties = mockk<Properties>()
        val fakeResult = mockk<Result>()
        val fakeObjects = mockk<Objects>()
        val fakeOutput = mockk<Output>()
        val fakeParameter = "flood"
        val expectedDefaultImageUrl = DisasterPropertiesHelper.getDisasterImage(fakeParameter)
        val expectedDefaultTitle = DisasterPropertiesHelper.getDisasterTitle(fakeParameter)
        val expectedDefaultDesc = DisasterPropertiesHelper.getDisasterDesc(fakeParameter)

        every { fakeGeometries.properties } returns fakeProperties
        every { fakeGeometries.coordinates } returns listOf(1.23, 4.56)
        every { fakeProperties.createdAt } returns "2040-04-10T12:34:56"
        every { fakeProperties.imageUrl } returns ""
        every { fakeProperties.title } returns ""
        every { fakeProperties.description } returns ""
        every { fakeProperties.disasterType } returns "flood"
        every { fakeProperties.reportData?.reportType } returns "flood on jakarta"
        every { fakeProperties.reportData?.flood_depth } returns 20
        every { fakeProperties.tags?.regionCode } returns "ID-ID"


        every { fakeResponse.result } returns fakeResult
        every { fakeResult.objects } returns fakeObjects
        every { fakeObjects.output } returns fakeOutput
        every { fakeOutput.geometries } returns listOf(fakeGeometries)

        val response = getResponseDisasterToModel(fakeResponse)
        assertEquals(1, response.size)
        val actualResponse = response[0]

        assertEquals(1.23, actualResponse.coordinates.latitude, 0.001)
        assertEquals(4.56, actualResponse.coordinates.longitude, 0.001)
        assertEquals("2040-04-10T12:34:56", actualResponse.createdAt)
        assertEquals("flood", actualResponse.disasterType)
        assertEquals("flood on jakarta", actualResponse.reportData.reportType)
        assertEquals(20, actualResponse.reportData.floodDepth)
        assertEquals("ID-ID", actualResponse.tags.regionCode)
        assertEquals(expectedDefaultImageUrl, actualResponse.imageUrl)
        assertEquals(expectedDefaultTitle, actualResponse.title)
        assertEquals(expectedDefaultDesc, actualResponse.description)

    }*/

    @Test
    fun `use getResponseDisasterToModel from MapperApiResponse Helper,when getResponseDisasterToModel get response is null, should return null`() {
        val fakeResponse: DisastersReportResponse? = null

        val actualResponse = getResponseDisasterToModel(fakeResponse)
        assertEquals(0, actualResponse.size)
    }

    @Test
    fun `use getResponseWeatherApiToModel from MapperApiResponse Helper, when getResponseWeatherApiToModel get response is not null, should return the data`() {
        val fakeObject = mockk<Current>()
        every { fakeObject.tempC } returns 25
        every { fakeObject.cloud } returns 60
        every { fakeObject.windMph } returns 5.5
        every { fakeObject.humidity } returns 75

        val fakeResponse = mockk<WeatherReportsResponse>()
        every { fakeResponse.current } returns fakeObject

        val actualResponse = getResponseWeatherApiToModel(fakeResponse)

        assertEquals(25, actualResponse.tempC)
        assertEquals(60, actualResponse.clouds)
        assertEquals(5.5, actualResponse.wind, 0.001)
        assertEquals(75, actualResponse.humidity)
    }

    @Test
    fun `use getResponseWeatherApiToModel from MapperApiResponse Helper, when getResponseWeatherApiToModel get response is null, should return null`() {

        val fakeResponse: WeatherReportsResponse? = null

        val actualResponse = getResponseWeatherApiToModel(fakeResponse)

        assertEquals(0, actualResponse.tempC)
        assertEquals(0, actualResponse.clouds)
        assertEquals(0.0, actualResponse.wind, 0.001)
        assertEquals(0, actualResponse.humidity)
    }


}