package ru.cft.shift2023winter.domain.entity

data class Location(
    val city: String,
    val localNames: HashMap<String, String>,
    val countryCode: String,
    val mapCoordinates: MapCoordinates
)
