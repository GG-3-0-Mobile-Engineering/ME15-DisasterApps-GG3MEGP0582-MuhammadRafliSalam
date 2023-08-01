package com.raflisalam.disastertracker.common.helper

import com.raflisalam.disastertracker.data.local.DisasterProperties

object DisasterPropertiesHelper {
    fun getDisasterImage(disasterType: String): String {
        return when (disasterType) {
            "earthquake" -> DisasterProperties.EARTHQUAKE.values.imageUrl
            "fire" -> DisasterProperties.FIRE.values.imageUrl
            "flood" -> DisasterProperties.FLOOD.values.imageUrl
            "haze" -> DisasterProperties.HAZE.values.imageUrl
            "volcano" -> DisasterProperties.VOLCANO.values.imageUrl
            else -> DisasterProperties.WIND.values.imageUrl
        }
    }

    fun getDisasterDesc(disasterType: String): String {
        return when (disasterType) {
            "earthquake" -> DisasterProperties.EARTHQUAKE.values.description
            "fire" -> DisasterProperties.FIRE.values.description
            "flood" -> DisasterProperties.FLOOD.values.description
            "haze" -> DisasterProperties.HAZE.values.description
            "volcano" -> DisasterProperties.VOLCANO.values.description
            "wind" -> DisasterProperties.WIND.values.description
            else -> "Disaster Not Found"
        }
    }

    fun getDisasterTitle(disasterType: String): String {
        return when (disasterType) {
            "earthquake" -> DisasterProperties.EARTHQUAKE.values.name
            "fire" -> DisasterProperties.FIRE.values.name
            "flood" -> DisasterProperties.FLOOD.values.name
            "haze" -> DisasterProperties.HAZE.values.name
            "volcano" -> DisasterProperties.VOLCANO.values.name
            "wind" -> DisasterProperties.WIND.values.name
            else -> "Disaster Not Found"
        }
    }
}
