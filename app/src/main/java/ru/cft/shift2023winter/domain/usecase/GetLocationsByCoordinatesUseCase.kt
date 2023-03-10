package ru.cft.shift2023winter.domain.usecase

import ru.cft.shift2023winter.domain.entity.Location
import ru.cft.shift2023winter.domain.entity.MapCoordinates
import ru.cft.shift2023winter.domain.repository.GeoCodingRepository

class GetLocationsByCoordinatesUseCase(private val geoCodingRepository: GeoCodingRepository) {
    suspend operator fun invoke(coordinates: MapCoordinates): List<Location> =
        geoCodingRepository.getLocationsByCoordinates(coordinates)
}