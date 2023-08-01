package com.raflisalam.disastertracker.data.local

enum class ReportsTimePeriod(val periodInSec: Int) {
    TODAY(86400),
    THREE_DAYS(259200),
    ONE_WEEK(604800)
}