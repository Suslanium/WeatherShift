package ru.cft.shift2023winter

import java.time.LocalDateTime
import java.time.ZoneOffset

object DefaultValues {
    const val DOUBLE = Double.NaN
    const val INT = -1
    const val LONG = -1L
    const val STRING = ""
    val TIME = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC)
}