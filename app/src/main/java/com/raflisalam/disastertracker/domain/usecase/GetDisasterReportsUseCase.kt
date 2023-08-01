package com.raflisalam.disastertracker.domain.usecase

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.DisasterReports
import kotlinx.coroutines.flow.Flow

interface GetDisasterReportsUseCase {
    suspend operator fun invoke(): Flow<Resource<List<DisasterReports>>>
}