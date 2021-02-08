package com.application.coronalive.api.response

class CityInformationResponse(
    val smallCityName: String?,
    val bigCityName: String,
    val TotalInfected: Int,
    val TotalInfectedInc: Int,
    val LiveInfected: Int,
    val LiveInfectedInc: Int
) {
}