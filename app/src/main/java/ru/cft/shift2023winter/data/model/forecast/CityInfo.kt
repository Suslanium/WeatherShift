package ru.cft.shift2023winter.data.model.forecast

import ru.cft.shift2023winter.DefaultValues
import java.time.LocalDateTime

data class CityInfo(
    val timezone: Long = DefaultValues.LONG,
    val sunrise: LocalDateTime?,
    val sunset: LocalDateTime?
)
