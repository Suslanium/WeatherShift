package ru.cft.shift2023winter.domain.repository

import ru.cft.shift2023winter.domain.entity.Location
import ru.cft.shift2023winter.domain.entity.MapCoordinates

interface GeoCodingRepository {
    suspend fun getPossibleLocationsByName(cityName: String): List<Location>

    suspend fun getLocationsByCoordinates(coordinates: MapCoordinates): List<Location>
}