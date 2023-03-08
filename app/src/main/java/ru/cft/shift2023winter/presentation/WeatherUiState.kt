package ru.cft.shift2023winter.presentation

import ru.cft.shift2023winter.presentation.adapter.FormattedForecast
import ru.cft.shift2023winter.presentation.adapter.FormattedWeather

sealed interface WeatherUiState {
    object Initial : WeatherUiState

    object Loading : WeatherUiState

    data class Content(
        val locationName: String,
        val currentWeather: FormattedWeather,
        val forecast: List<FormattedForecast>
    ) : WeatherUiState

    data class Error(val message: String) : WeatherUiState
}