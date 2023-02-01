package ru.cft.shift2023winter.data.datasource

import ru.cft.shift2023winter.domain.entity.*

interface WeatherNetworkDataSource {
    suspend fun fetchWeather(weatherRequestParams: WeatherRequestParams): Weather

    suspend fun fetchForecast(weatherRequestParams: WeatherRequestParams): Forecast
}