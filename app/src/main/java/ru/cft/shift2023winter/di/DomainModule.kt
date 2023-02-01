package ru.cft.shift2023winter.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.cft.shift2023winter.data.datasource.GeoCodingDataSource
import ru.cft.shift2023winter.data.datasource.SettingsDataSource
import ru.cft.shift2023winter.data.datasource.WeatherCacheDataSource
import ru.cft.shift2023winter.data.datasource.WeatherNetworkDataSource
import ru.cft.shift2023winter.data.repository.GeoCodingRepositoryImpl
import ru.cft.shift2023winter.data.repository.SettingsRepositoryImpl
import ru.cft.shift2023winter.data.repository.WeatherRepositoryImpl
import ru.cft.shift2023winter.domain.repository.GeoCodingRepository
import ru.cft.shift2023winter.domain.repository.SettingsRepository
import ru.cft.shift2023winter.domain.repository.WeatherRepository
import ru.cft.shift2023winter.domain.usecase.*

private fun provideWeatherRepository(
    cacheDataSource: WeatherCacheDataSource, networkDataSource: WeatherNetworkDataSource
): WeatherRepository = WeatherRepositoryImpl(cacheDataSource, networkDataSource)

private fun provideGeoCodingRepository(
    geoCodingDataSource: GeoCodingDataSource
): GeoCodingRepository = GeoCodingRepositoryImpl(geoCodingDataSource)

private fun provideSettingsRepository(
    settingsDataSource: SettingsDataSource
): SettingsRepository = SettingsRepositoryImpl(settingsDataSource)

fun provideDomainModule(): Module = module {
    single {
        provideWeatherRepository(get(), get())
    }
    single {
        provideGeoCodingRepository(get())
    }
    single {
        provideSettingsRepository(get())
    }
    factory {
        GetForecastUseCase(get(), get())
    }
    factory {
        GetLocationsByCoordinatesUseCase(get())
    }
    factory {
        GetLocationsByNameUseCase(get())
    }
    factory {
        GetSettingsUseCase(get())
    }
    factory {
        GetWeatherUseCase(get(),get())
    }
    factory {
        SetSettingsUseCase(get())
    }
    factory {
        UpdateForecastUseCase(get(),get())
    }
    factory {
        UpdateWeatherUseCase(get(),get())
    }
}