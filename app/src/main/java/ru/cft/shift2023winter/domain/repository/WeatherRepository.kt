package ru.cft.shift2023winter.domain.repository

import ru.cft.shift2023winter.domain.entity.*

interface WeatherRepository {
    suspend fun getCurrentWeather(weatherRequestParams: WeatherRequestParams): Weather

    suspend fun updateCurrentWeather(weatherRequestParams: WeatherRequestParams): Weather

    suspend fun getForecast(weatherRequestParams: WeatherRequestParams): Forecast

    suspend fun updateForecast(weatherRequestParams: WeatherRequestParams): Forecast
}