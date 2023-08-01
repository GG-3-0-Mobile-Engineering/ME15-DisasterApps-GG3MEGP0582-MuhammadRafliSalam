package com.raflisalam.disastertracker.domain.repository

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.DisasterReports
import kotlinx.coroutines.flow.Flow


interface DisastersRepository {

    suspend fun fetchDisasterReports(timePeriod: Number): Flow<Resource<List<DisasterReports>>>

}