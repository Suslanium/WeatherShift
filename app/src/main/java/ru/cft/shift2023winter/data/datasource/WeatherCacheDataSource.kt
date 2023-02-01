package ru.cft.shift2023winter.data.datasource

import ru.cft.shift2023winter.domain.entity.*

interface WeatherCacheDataSource {
    suspend fun getCachedWeather(weatherRequestParams: WeatherRequestParams): Weather?

    suspend fun getCachedForecast(weatherRequestParams: WeatherRequestParams): Forecast?

    suspend fun saveWeather(weather: Weather, weatherRequestParams: WeatherRequestParams)

    suspend fun saveForecast(forecast: Forecast, weatherRequestParams: WeatherRequestParams)
}