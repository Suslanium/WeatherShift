package ru.cft.shift2023winter.domain.entity

data class WeatherRequestParams(
    val coordinates: MapCoordinates,
    val unitType: UnitType,
    val languageCode: LanguageCode
)