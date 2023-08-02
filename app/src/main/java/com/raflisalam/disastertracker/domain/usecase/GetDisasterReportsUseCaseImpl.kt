package com.raflisalam.disastertracker.domain.usecase

import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.common.helper.Convert
import com.raflisalam.disastertracker.common.utils.NotificationUtils
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.repository.DisastersRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class GetDisasterReportsUseCaseImpl @Inject constructor(
    private val repository: DisastersRepository,
    private val notificationUtils: NotificationUtils
) : GetDisasterReportsUseCase {
    override suspend fun invoke(
        regionName: String?,
        disaster: String?,
        timePeriod: Number
    ): Flow<Resource<List<DisasterReports>>> {
        val regionCode = regionName?.let { Convert.regionNameToRegionCode(it) }
        return repository.fetchDisasterReports(regionCode, disaster, timePeriod)
    }

    override fun checkAndSendPushNotification(reportType: String, floodDepth: Int) {
        notificationUtils.checkAndSendPushNotification(reportType, floodDepth)
    }
}