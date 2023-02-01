package ru.cft.shift2023winter.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.cft.shift2023winter.data.model.location.LocationModel

interface GeoCodingApi {
    @GET("geo/1.0/direct")
    suspend fun getLocationsByName(
        @Query("q") locationName: String,
        @Query("appid") apiKey: String
    ): List<LocationModel>

    @GET("geo/1.0/reverse")
    suspend fun getLocationsByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): List<LocationModel>
}