package com.raflisalam.disastertracker.domain.model

data class DisasterReportsState(
    val isLoading: Boolean = false,
    val reports: List<DisasterReports> = emptyList(),
    val error: String = ""
)