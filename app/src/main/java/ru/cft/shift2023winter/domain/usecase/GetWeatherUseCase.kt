package ru.cft.shift2023winter.domain.usecase

import ru.cft.shift2023winter.domain.entity.Weather
import ru.cft.shift2023winter.domain.entity.WeatherRequestParams
import ru.cft.shift2023winter.domain.repository.SettingsRepository
import ru.cft.shift2023winter.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Result<Weather> {
        val settings = settingsRepository.getSettings()
        val currentLocation = settings.location
            ?: return Result.failure(Exception("Current location not set"))
        return try {
            val result = weatherRepository.getCurrentWeather(
                WeatherRequestParams(
                    currentLocation.mapCoordinates,
                    settings.unitType,
                    settings.languageCode
                )
            )
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}