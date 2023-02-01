package ru.cft.shift2023winter.data.datasource

import ru.cft.shift2023winter.data.api.ApiKey
import ru.cft.shift2023winter.data.api.GeoCodingApi
import ru.cft.shift2023winter.data.converter.LocationConverter
import ru.cft.shift2023winter.domain.entity.Location
import ru.cft.shift2023winter.domain.entity.MapCoordinates

class GeoCodingDataSourceImpl(
    private val geoCodingApi: GeoCodingApi,
    private val apiKey: ApiKey,
    private val locationConverter: LocationConverter
) : GeoCodingDataSource {
    override suspend fun getLocationsByCoordinates(mapCoordinates: MapCoordinates): List<Location> {
        val response = geoCodingApi.getLocationsByCoordinates(
            mapCoordinates.latitude,
            mapCoordinates.longitude,
            apiKey.apiKey
        )
        return response.map { locationConverter.convert(it) }
    }

    override suspend fun getLocationsByName(name: String): List<Location> {
        val response = geoCodingApi.getLocationsByName(name, apiKey.apiKey)
        return response.map { locationConverter.convert(it) }
    }
}