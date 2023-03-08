package ru.cft.shift2023winter.presentation

sealed interface LocationUiState {
    object Initial : LocationUiState

    object Loading : LocationUiState

    object Finished : LocationUiState

    data class Content(val names: List<String>) : LocationUiState
}