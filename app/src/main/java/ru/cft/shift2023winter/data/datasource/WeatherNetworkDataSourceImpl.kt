package ru.cft.shift2023winter.data.datasource

import ru.cft.shift2023winter.data.api.ApiKey
import ru.cft.shift2023winter.data.api.WeatherApi
import ru.cft.shift2023winter.data.converter.ForecastConverter
import ru.cft.shift2023winter.data.converter.WeatherConverter
import ru.cft.shift2023winter.domain.entity.Forecast
import ru.cft.shift2023winter.domain.entity.Weather
import ru.cft.shift2023winter.domain.entity.WeatherRequestParams

class WeatherNetworkDataSourceImpl(
    private val weatherApi: WeatherApi,
    private val apiKey: ApiKey,
    private val forecastConverter: ForecastConverter,
    private val weatherConverter: WeatherConverter
) : WeatherNetworkDataSource {

    override suspend fun fetchWeather(weatherRequestParams: WeatherRequestParams): Weather {
        val response = weatherApi.getWeather(
            weatherRequestParams.coordinates.latitude,
            weatherRequestParams.coordinates.longitude,
            apiKey.apiKey,
            weatherRequestParams.unitType.name,
            weatherRequestParams.languageCode.name
        )
        return weatherConverter.convert(response)
    }

    override suspend fun fetchForecast(weatherRequestParams: WeatherRequestParams): Forecast {
        val response = weatherApi.getForecast(
            weatherRequestParams.coordinates.latitude,
            weatherRequestParams.coordinates.longitude,
            apiKey.apiKey,
            weatherRequestParams.unitType.name,
            weatherRequestParams.languageCode.name
        )
        return forecastConverter.convert(response)
    }
}