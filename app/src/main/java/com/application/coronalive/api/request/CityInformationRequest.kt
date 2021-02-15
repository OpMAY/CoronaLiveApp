package com.application.coronalive.api.request

data class CityInformationRequest(
    val bigCityName: String,
    val smallCityName: String?
) {
}