package ru.cft.shift2023winter.presentation.adapter

import androidx.annotation.DrawableRes

data class WeatherParameter(
    val name: String, @DrawableRes val id: Int, val value: String
)
