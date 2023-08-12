package com.raflisalam.disastertracker.data.repository

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.common.helper.getResponseDisasterToModel
import com.raflisalam.disastertracker.data.remote.services.DisastersApi
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.repository.DisastersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

import javax.inject.Inject


class DisasterRepositoryImpl @Inject constructor(
    private val apiServices: DisastersApi
    ) : DisastersRepository {

    override suspend fun fetchDisasterReports(
        regionName: String?,
        disaster: String?,
        timePeriod: Number
    ): Flow<Resource<List<DisasterReports>>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = apiServices.getDisasterReports(regionName, disaster, timePeriod)
            if (apiResponse.isSuccessful) {
                val disasterReportsResponse = apiResponse.body()
                val disasterReportsList = getResponseDisasterToModel(disasterReportsResponse)
                emit(Resource.Success(disasterReportsList))
            } else {
                emit(Resource.Error("API request failed with code ${apiResponse.code()}"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }  catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}
