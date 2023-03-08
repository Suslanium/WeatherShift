package ru.cft.shift2023winter.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cft.shift2023winter.domain.usecase.*
import ru.cft.shift2023winter.presentation.adapter.WeatherAdapter

class WeatherViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val updateForecastUseCase: UpdateForecastUseCase,
    private val weatherAdapter: WeatherAdapter
) : ViewModel() {
    private val _state: MutableLiveData<WeatherUiState> = MutableLiveData()
    val state: LiveData<WeatherUiState> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ViewModel", throwable.stackTraceToString())
        _state.postValue(WeatherUiState.Error(throwable.message?:""))
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _state.postValue(WeatherUiState.Loading)
            val weather = getWeatherUseCase()
            val forecast = getForecastUseCase()
            val settings = getSettingsUseCase()
            val formattedWeather = weatherAdapter.getFormattedWeather(weather, settings.unitType)
            val formattedForecast =
                weatherAdapter.getFormattedForecastList(forecast, settings.unitType)
            if (settings.location!!.localNames.keys.contains(settings.languageCode.name.lowercase())) {
                _state.postValue(
                    WeatherUiState.Content(
                        settings.location.localNames[settings.languageCode.name.lowercase()]
                            ?: settings.location.city, formattedWeather, formattedForecast
                    )
                )
            } else {
                _state.postValue(
                    WeatherUiState.Content(
                        settings.location.city, formattedWeather, formattedForecast
                    )
                )
            }
        }
    }

    fun updateData() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _state.postValue(WeatherUiState.Loading)
            val weather = updateWeatherUseCase()
            val forecast = updateForecastUseCase()
            val settings = getSettingsUseCase()
            val formattedWeather = weatherAdapter.getFormattedWeather(weather, settings.unitType)
            val formattedForecast =
                weatherAdapter.getFormattedForecastList(forecast, settings.unitType)
            if (settings.location!!.localNames.keys.contains(settings.languageCode.name.lowercase())) {
                _state.postValue(
                    WeatherUiState.Content(
                        settings.location.localNames[settings.languageCode.name.lowercase()]
                            ?: settings.location.city, formattedWeather, formattedForecast
                    )
                )
            } else {
                _state.postValue(
                    WeatherUiState.Content(
                        settings.location.city, formattedWeather, formattedForecast
                    )
                )
            }
        }
    }
}