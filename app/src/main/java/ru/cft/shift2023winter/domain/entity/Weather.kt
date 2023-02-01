package ru.cft.shift2023winter.domain.entity

import java.time.LocalDateTime

data class Weather(
    val weatherDescription: String,
    val weatherIconId: String,
    val temperature: Double,
    val temperatureFeelsLike: Double,
    val pressure: Double,
    val humidity: Int,
    val minZoneTemperature: Double,
    val maxZoneTemperature: Double,
    val visibility: Int,
    val windSpeed: Double,
    val windDirectionDegree: Int,
    val windGustSpeed: Double,
    val cloudiness: Int,
    val rainVolumeHour: Double,
    val rainVolumeThreeHours: Double,
    val snowVolumeHour: Double,
    val snowVolumeThreeHours: Double,
    val sunriseTime: LocalDateTime,
    val sunsetTime: LocalDateTime,
    val timeZoneShift: Long,
    val timeStamp: LocalDateTime
)
