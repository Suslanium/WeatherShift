package ru.cft.shift2023winter.domain.usecase

import ru.cft.shift2023winter.domain.entity.Location
import ru.cft.shift2023winter.domain.repository.GeoCodingRepository

class GetLocationsByNameUseCase(private val geoCodingRepository: GeoCodingRepository) {
    suspend operator fun invoke(locationName: String): Result<List<Location>> {
        return try {
            val result = geoCodingRepository.getPossibleLocationsByName(locationName)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}