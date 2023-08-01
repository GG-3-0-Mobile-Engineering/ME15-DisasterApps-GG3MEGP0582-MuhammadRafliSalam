package com.raflisalam.disastertracker.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.raflisalam.disastertracker.domain.model.DisasterReports

data class DisastersReportResponse(
    @SerializedName("statusCode")
    val statusCode: Int? = null,
    @SerializedName("result")
    val result: Result? = null
)

data class Result(
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("objects")
    val objects: Objects? = null
)

data class Objects(
    @SerializedName("output")
    val output: Output? = null
)

data class Output(
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("geometries")
    val geometries: List<Geometry>? = null
)

data class Geometry(
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("properties")
    val properties: PropertiesDto? = null,
    @SerializedName("coordinates")
    val coordinates: List<Double>? = null
)

data class PropertiesDto(
    @SerializedName("pkey")
    val pkey: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("source")
    val source: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("disaster_type")
    val disasterType: String? = null,
    @SerializedName("report_data")
    val reportData: ReportData? = null,
    @SerializedName("tags")
    val tags: Tags? = null,
    @SerializedName("title")
    val title: String?,
    @SerializedName("text")
    val description: String?,
    @SerializedName("partner_code")
    val partnerCode: String?,
    @SerializedName("partner_icon")
    val partnerIcon: String?
)

data class ReportData(
    @SerializedName("report_type")
    val reportType: String,
    @SerializedName("flood_depth")
    val flood_depth: Int
)

data class Tags(
    @SerializedName("district_id")
    val districtId: String?,
    @SerializedName("region_code")
    val regionCode: String?,
    @SerializedName("local_area_id")
    val localAreaId: String?,
    @SerializedName("instance_region_code")
    val instanceRegionCode: String?
)
