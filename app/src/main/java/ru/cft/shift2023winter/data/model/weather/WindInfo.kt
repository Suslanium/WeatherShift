package ru.cft.shift2023winter.data.model.weather

import ru.cft.shift2023winter.data.DefaultValues

data class WindInfo(
    val speed: Double = DefaultValues.DOUBLE,
    val deg: Int = DefaultValues.INT,
    val gust: Double = DefaultValues.DOUBLE
)
