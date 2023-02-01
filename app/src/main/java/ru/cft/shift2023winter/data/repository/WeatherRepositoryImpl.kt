package ru.cft.shift2023winter.data.repository

import ru.cft.shift2023winter.data.datasource.WeatherCacheDataSource
import ru.cft.shift2023winter.data.datasource.WeatherNetworkDataSource
import ru.cft.shift2023winter.domain.entity.Forecast
import ru.cft.shift2023winter.domain.entity.Weather
import ru.cft.shift2023winter.domain.entity.WeatherRequestParams
import ru.cft.shift2023winter.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val cacheDataSource: WeatherCacheDataSource,
    private val networkDataSource: WeatherNetworkDataSource
) : WeatherRepository {
    override suspend fun getCurrentWeather(weatherRequestParams: WeatherRequestParams): Weather {
        return cacheDataSource.getCachedWeather(weatherRequestParams)
            ?: updateCurrentWeather(weatherRequestParams)
    }

    override suspend fun updateCurrentWeather(weatherRequestParams: WeatherRequestParams): Weather {
        val updatedWeather = networkDataSource.fetchWeather(weatherRequestParams)
        cacheDataSource.saveWeather(updatedWeather,weatherRequestParams)
        return updatedWeather
    }

    override suspend fun getForecast(weatherRequestParams: WeatherRequestParams): Forecast {
        return cacheDataSource.getCachedForecast(weatherRequestParams)
            ?: updateForecast(weatherRequestParams)
    }

    override suspend fun updateForecast(weatherRequestParams: WeatherRequestParams): Forecast {
        val updatedForecast = networkDataSource.fetchForecast(weatherRequestParams)
        cacheDataSource.saveForecast(updatedForecast,weatherRequestParams)
        return updatedForecast
    }

}