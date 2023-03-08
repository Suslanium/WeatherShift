package ru.cft.shift2023winter.domain.usecase

import ru.cft.shift2023winter.domain.entity.Forecast
import ru.cft.shift2023winter.domain.entity.WeatherRequestParams
import ru.cft.shift2023winter.domain.repository.SettingsRepository
import ru.cft.shift2023winter.domain.repository.WeatherRepository

class GetForecastUseCase(
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Forecast {
        val settings = settingsRepository.getSettings()
        val currentLocation = settings.location
            ?: throw Exception("Current location not set")
        return weatherRepository.getForecast(
            WeatherRequestParams(
                currentLocation.mapCoordinates,
                settings.unitType,
                settings.languageCode
            )
        )
    }
}