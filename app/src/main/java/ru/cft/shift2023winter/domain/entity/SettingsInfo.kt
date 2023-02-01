package ru.cft.shift2023winter.domain.entity

data class SettingsInfo(
    val location: Location?,
    val languageCode: LanguageCode,
    val unitType: UnitType,
    val updateInterval: UpdateInterval,
    val usesGeolocation: Boolean
)
