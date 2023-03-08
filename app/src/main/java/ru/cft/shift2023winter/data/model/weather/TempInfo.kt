package ru.cft.shift2023winter.data.model.weather

import ru.cft.shift2023winter.DefaultValues

data class TempInfo(
    val temp: Double = DefaultValues.DOUBLE,
    val feels_like: Double = DefaultValues.DOUBLE,
    val temp_min: Double = DefaultValues.DOUBLE,
    val temp_max: Double = DefaultValues.DOUBLE,
    val pressure: Double = DefaultValues.DOUBLE,
    val humidity: Int = DefaultValues.INT
)
