package ru.cft.shift2023winter.presentation.adapter

import androidx.annotation.DrawableRes

data class FormattedWeather(
    @DrawableRes val id: Int,
    val description: String,
    val temperature: String,
    val time: String,
    val parameters: List<WeatherParameter>
)
