package ru.cft.shift2023winter.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.cft.shift2023winter.data.LocalDateTimeSerializer
import ru.cft.shift2023winter.data.api.ApiKey
import ru.cft.shift2023winter.data.api.GeoCodingApi
import ru.cft.shift2023winter.data.api.WeatherApi
import ru.cft.shift2023winter.data.converter.ForecastConverter
import ru.cft.shift2023winter.data.converter.LocationConverter
import ru.cft.shift2023winter.data.converter.WeatherConverter
import ru.cft.shift2023winter.data.datasource.*
import java.io.File
import java.time.LocalDateTime

private fun provideGeoCodingDataSource(
    geoCodingApi: GeoCodingApi, apiKey: ApiKey, locationConverter: LocationConverter
): GeoCodingDataSource = GeoCodingDataSourceImpl(geoCodingApi, apiKey, locationConverter)

private fun provideWeatherNetworkDataSource(
    weatherApi: WeatherApi,
    apiKey: ApiKey,
    forecastConverter: ForecastConverter,
    weatherConverter: WeatherConverter
): WeatherNetworkDataSource =
    WeatherNetworkDataSourceImpl(weatherApi, apiKey, forecastConverter, weatherConverter)

private fun provideWeatherCacheDataSource(
    deserializerGson: Gson, serializerGson: Gson, cacheDirectory: File
): WeatherCacheDataSource =
    WeatherCacheDataSourceImpl(deserializerGson, serializerGson, cacheDirectory)

private fun provideSettingsDataSource(
    deserializerGson: Gson, serializerGson: Gson, fileDirectory: File
): SettingsDataSource = SettingsDataSourceImpl(deserializerGson, serializerGson, fileDirectory)

private fun provideSerializerGson(): Gson =
    GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer)
        .serializeSpecialFloatingPointValues().create()

private fun provideCacheDirectory(context: Context): File = context.cacheDir

private fun provideFileDirectory(context: Context): File = context.filesDir

fun provideDataModule(): Module = module {
    factory {
        WeatherConverter()
    }
    factory {
        ForecastConverter()
    }
    factory {
        LocationConverter()
    }
    factory(named("serializerGson")) {
        provideSerializerGson()
    }
    factory(named("cache")) {
        provideCacheDirectory(androidContext())
    }
    factory(named("file")) {
        provideFileDirectory(androidContext())
    }
    single {
        provideGeoCodingDataSource(get(), get(), get())
    }
    single {
        provideWeatherNetworkDataSource(get(), get(), get(), get())
    }
    single {
        provideWeatherCacheDataSource(
            get(named("deserializerGson")), get(named("serializerGson")), get(named("cache"))
        )
    }
    single {
        provideSettingsDataSource(
            get(named("deserializerGson")), get(named("serializerGson")), get(named("file"))
        )
    }
}