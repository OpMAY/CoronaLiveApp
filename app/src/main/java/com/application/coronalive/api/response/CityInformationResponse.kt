package com.application.coronalive.api.response

data class CityInformationResponse(
    val smallCityName: String?,
    val bigCityName: String,
    val totalInfected: Int,
    val totalInfectedInc: Int,
    val liveInfected: Int,
    val liveInfectedInc: Int
) {
}