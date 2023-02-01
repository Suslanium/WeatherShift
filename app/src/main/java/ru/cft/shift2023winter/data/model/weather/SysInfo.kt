package ru.cft.shift2023winter.data.model.weather

import java.time.LocalDateTime

data class SysInfo(
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime
)
