package ru.cft.shift2023winter.data.repository

import ru.cft.shift2023winter.data.datasource.GeoCodingDataSource
import ru.cft.shift2023winter.domain.entity.Location
import ru.cft.shift2023winter.domain.entity.MapCoordinates
import ru.cft.shift2023winter.domain.repository.GeoCodingRepository

class GeoCodingRepositoryImpl(private val geoCodingDataSource: GeoCodingDataSource) :
    GeoCodingRepository {
    override suspend fun getPossibleLocationsByName(cityName: String): List<Location> =
        geoCodingDataSource.getLocationsByName(cityName)

    override suspend fun getLocationsByCoordinates(coordinates: MapCoordinates): List<Location> =
        geoCodingDataSource.getLocationsByCoordinates(coordinates)
}