package ru.cft.shift2023winter.domain.usecase

import ru.cft.shift2023winter.domain.entity.Weather
import ru.cft.shift2023winter.domain.entity.WeatherRequestParams
import ru.cft.shift2023winter.domain.repository.SettingsRepository
import ru.cft.shift2023winter.domain.repository.WeatherRepository

class UpdateWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Weather {
        val settings = settingsRepository.getSettings()
        val currentLocation = settings.location ?: throw Exception("Current location not set")
        return weatherRepository.updateCurrentWeather(
            WeatherRequestParams(
                currentLocation.mapCoordinates, settings.unitType, settings.languageCode
            )
        )
    }
}