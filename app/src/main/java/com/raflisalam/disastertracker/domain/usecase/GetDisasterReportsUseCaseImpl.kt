package com.raflisalam.disastertracker.domain.usecase

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.repository.DisastersRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class GetDisasterReportsUseCaseImpl @Inject constructor(
    private val repository: DisastersRepository
) : GetDisasterReportsUseCase {
    override suspend fun invoke(): Flow<Resource<List<DisasterReports>>> = repository.fetchDisasterReports(604800)
}