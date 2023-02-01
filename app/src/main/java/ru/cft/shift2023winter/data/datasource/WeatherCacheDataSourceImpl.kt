package ru.cft.shift2023winter.data.datasource

import android.util.Log
import com.google.gson.Gson
import ru.cft.shift2023winter.domain.entity.Forecast
import ru.cft.shift2023winter.domain.entity.Weather
import ru.cft.shift2023winter.domain.entity.WeatherRequestParams
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class WeatherCacheDataSourceImpl(
    private val deserializerGson: Gson, private val serializerGson: Gson, cacheDirectory: File
) : WeatherCacheDataSource {
    companion object {
        private const val ERROR_TAG = "CacheIO"
    }

    private val cacheWeatherSettingsFile =
        File(cacheDirectory.path + File.separator + "WeatherSettings.json")
    private val cacheForecastSettingsFile =
        File(cacheDirectory.path + File.separator + "ForecastSettings.json")
    private val cacheForecastFile = File(cacheDirectory.path + File.separator + "Forecast.json")
    private val cacheWeatherFile = File(cacheDirectory.path + File.separator + "Weather.json")


    override suspend fun getCachedWeather(weatherRequestParams: WeatherRequestParams): Weather? {
        try {
            if (cacheWeatherFile.exists() && cacheWeatherSettingsFile.exists()) {
                val settingsReader = FileReader(cacheWeatherSettingsFile)
                val savedSettings = deserializerGson.fromJson(
                    settingsReader, WeatherRequestParams::class.java
                )
                settingsReader.close()
                if (savedSettings == weatherRequestParams) {
                    val weatherReader = FileReader(cacheWeatherFile)
                    val result = deserializerGson.fromJson(
                        weatherReader, Weather::class.java
                    )
                    weatherReader.close()
                    return result
                }
            }
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.stackTraceToString())
        }
        return null
    }

    override suspend fun getCachedForecast(weatherRequestParams: WeatherRequestParams): Forecast? {
        try {
            if (cacheForecastFile.exists() && cacheForecastSettingsFile.exists()) {
                val settingsReader = FileReader(cacheForecastSettingsFile)
                val savedSettings = deserializerGson.fromJson(
                    settingsReader, WeatherRequestParams::class.java
                )
                settingsReader.close()
                if (savedSettings == weatherRequestParams) {
                    val forecastReader = FileReader(cacheForecastFile)
                    val result = deserializerGson.fromJson(
                        forecastReader, Forecast::class.java
                    )
                    forecastReader.close()
                    return result
                }
            }
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.stackTraceToString())
        }
        return null
    }

    override suspend fun saveWeather(weather: Weather, weatherRequestParams: WeatherRequestParams) {
        try {
            cacheWeatherSettingsFile.delete()
            val settingsWriter = FileWriter(cacheWeatherSettingsFile)
            serializerGson.toJson(weatherRequestParams, settingsWriter)
            settingsWriter.flush()
            settingsWriter.close()
            cacheWeatherFile.delete()
            val cacheWeatherWriter = FileWriter(cacheWeatherFile)
            serializerGson.toJson(weather, cacheWeatherWriter)
            cacheWeatherWriter.flush()
            cacheWeatherWriter.close()
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.stackTraceToString())
        }
    }

    override suspend fun saveForecast(
        forecast: Forecast, weatherRequestParams: WeatherRequestParams
    ) {
        try {
            cacheForecastSettingsFile.delete()
            val settingsWriter = FileWriter(cacheForecastSettingsFile)
            serializerGson.toJson(weatherRequestParams, settingsWriter)
            settingsWriter.flush()
            settingsWriter.close()
            cacheForecastFile.delete()
            val cacheForecastWriter = FileWriter(cacheForecastFile)
            serializerGson.toJson(forecast, cacheForecastWriter)
            cacheForecastWriter.flush()
            cacheForecastWriter.close()
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.stackTraceToString())
        }
    }
}