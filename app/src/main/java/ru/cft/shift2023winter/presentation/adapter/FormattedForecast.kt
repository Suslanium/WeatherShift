package ru.cft.shift2023winter.presentation.adapter

data class FormattedForecast(
    val dayOfWeek: String,
    val dayNightAverageTemperature: String,
    val hourlyItems: List<FormattedWeather>
)