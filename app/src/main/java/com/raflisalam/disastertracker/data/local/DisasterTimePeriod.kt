package com.raflisalam.disastertracker.data.local

enum class DisasterTimePeriod(val periodInSec: Number) {
    TODAY(86400),
    THREE_DAYS(259200),
    ONE_WEEKS(604800)
}