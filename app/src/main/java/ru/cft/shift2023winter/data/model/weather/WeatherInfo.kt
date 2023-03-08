package ru.cft.shift2023winter.data.model.weather

import ru.cft.shift2023winter.DefaultValues

data class WeatherInfo(
    val description: String = DefaultValues.STRING,
    val icon: String = DefaultValues.STRING
)