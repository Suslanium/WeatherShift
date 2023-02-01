package ru.cft.shift2023winter.data.model.weather

import ru.cft.shift2023winter.data.DefaultValues
import java.time.LocalDateTime

data class WeatherModel(
    val weather: List<WeatherInfo>?,
    val main: TempInfo?,
    val visibility: Int?,
    val wind: WindInfo?,
    val rain: RainInfo?,
    val snow: SnowInfo?,
    val clouds: CloudsInfo?,
    val dt: LocalDateTime?,
    val sys: SysInfo?,
    val timezone: Long = DefaultValues.LONG
)
