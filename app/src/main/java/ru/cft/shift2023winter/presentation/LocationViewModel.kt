package ru.cft.shift2023winter.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.cft.shift2023winter.DefaultValues
import ru.cft.shift2023winter.domain.entity.Location
import ru.cft.shift2023winter.domain.entity.SettingsInfo
import ru.cft.shift2023winter.domain.usecase.GetLocationsByNameUseCase
import ru.cft.shift2023winter.domain.usecase.GetSettingsUseCase
import ru.cft.shift2023winter.domain.usecase.SetSettingsUseCase

class LocationViewModel(
    private val getLocationsByNameUseCase: GetLocationsByNameUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val setSettingsUseCase: SetSettingsUseCase
) : ViewModel() {
    private val _state: MutableLiveData<LocationUiState> = MutableLiveData()
    private var currentLocationList: List<Location> = listOf()
    val state: LiveData<LocationUiState> = _state
    private val _inputFlow = MutableStateFlow("")
    val inputFlow: StateFlow<String> = _inputFlow.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ViewModel", throwable.stackTraceToString())
    }

    init {
        viewModelScope.launch {
            inputFlow.debounce(1000).collect {
                if (it != "") {
                    getLocationsByName(it)
                }
            }
        }
    }

    fun setInput(input: String) {
        _inputFlow.value = input
    }

    private fun getLocationsByName(name: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _state.postValue(LocationUiState.Loading)
            val result = getLocationsByNameUseCase.invoke(name)
            val settings = getSettingsUseCase()
            val listToShow = mutableListOf<String>()
            for (location in result) {
                listToShow.add(if (location.localNames.containsKey(settings.languageCode.name.lowercase())) location.localNames[settings.languageCode.name.lowercase()]!! else location.city)
                if (location.state != DefaultValues.STRING) {
                    listToShow[listToShow.lastIndex] = listToShow.last().plus(", ${location.state}")
                }
                if (location.countryCode != DefaultValues.STRING) {
                    listToShow[listToShow.lastIndex] =
                        listToShow.last().plus(", ${location.countryCode}")
                }
            }
            currentLocationList = result
            _state.postValue(LocationUiState.Content(listToShow))
        }
    }

    fun setLocation(index: Int) {
        if (_state.value is LocationUiState.Content) {
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                _state.postValue(LocationUiState.Loading)
                val settings = getSettingsUseCase()
                setSettingsUseCase(
                    SettingsInfo(
                        currentLocationList[index],
                        settings.languageCode,
                        settings.unitType,
                        settings.updateInterval,
                        false
                    )
                )
                _state.postValue(LocationUiState.Finished)
            }
        }
    }
}