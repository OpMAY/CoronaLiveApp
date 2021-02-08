package com.application.coronalive.api

import com.application.coronalive.api.request.CityInformationRequest
import com.application.coronalive.api.response.ApiResponse
import com.application.coronalive.api.response.CityInformationResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface CLApi {
    companion object{
        val instance = ApiGenerator()
            .generate(CLApi::class.java)
    }
    @GET("/api/v1/cities")
    suspend fun search(
        @Body request : CityInformationRequest
    ): ApiResponse<CityInformationResponse>

    @GET("/api/v1/all_cities")
    suspend fun showList() : ApiResponse<CityInformationResponse>

    @GET("/api/v1/big_cities")
    suspend fun showBigCityList() : ApiResponse<CityInformationResponse>
}