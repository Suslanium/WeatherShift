package ru.cft.shift2023winter.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.cft.shift2023winter.data.model.forecast.ForecastModel
import ru.cft.shift2023winter.data.model.weather.WeatherModel

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") key: String,
        @Query("units") units: String,
        @Query("lang") langCode: String
    ): WeatherModel

    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") key: String,
        @Query("units") units: String,
        @Query("lang") langCode: String
    ): ForecastModel
}