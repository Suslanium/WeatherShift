package ru.cft.shift2023winter.data.datasource

import ru.cft.shift2023winter.domain.entity.*

interface GeoCodingDataSource {
    suspend fun getLocationsByCoordinates(mapCoordinates: MapCoordinates): List<Location>

    suspend fun getLocationsByName(name: String): List<Location>
}