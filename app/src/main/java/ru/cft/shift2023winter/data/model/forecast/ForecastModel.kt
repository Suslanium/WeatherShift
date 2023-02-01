package ru.cft.shift2023winter.data.model.forecast

import ru.cft.shift2023winter.data.model.weather.WeatherModel

data class ForecastModel(
    val list: List<WeatherModel>,
    val city: CityInfo?
)
