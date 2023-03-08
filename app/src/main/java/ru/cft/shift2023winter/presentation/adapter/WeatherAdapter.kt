package ru.cft.shift2023winter.presentation.adapter

import android.content.res.Resources
import ru.cft.shift2023winter.DefaultValues
import ru.cft.shift2023winter.R
import ru.cft.shift2023winter.domain.entity.Forecast
import ru.cft.shift2023winter.domain.entity.UnitType
import ru.cft.shift2023winter.domain.entity.Weather
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class WeatherAdapter(private val resources: Resources) {

    fun getFormattedForecastList(
        forecast: Forecast, unitType: UnitType
    ): List<FormattedForecast> {
        val dailyForecast: HashMap<LocalDate, MutableList<Weather>> = hashMapOf()
        for (weather in forecast.forecastList) {
            val date =
                weather.timeStamp.plusSeconds(OffsetDateTime.now().offset.totalSeconds.toLong())
                    .toLocalDate()
            if (dailyForecast.containsKey(date)) {
                dailyForecast[date]?.add(weather)
            } else {
                dailyForecast[date] = mutableListOf(weather)
            }
        }
        val result = mutableListOf<FormattedForecast>()
        for (day in dailyForecast.keys.reversed()) {
            var dayAverageTemp = 0
            var nightAverageTemp = 0
            var dayEntriesCount = 0
            var nightEntriesCount = 0
            for (weather in dailyForecast[day]!!) {
                if (weather.weatherIconId.contains("n")) {
                    nightAverageTemp += weather.temperature.toInt()
                    nightEntriesCount++
                } else if (weather.weatherIconId.contains("d")) {
                    dayAverageTemp += weather.temperature.toInt()
                    dayEntriesCount++
                }
            }
            if (dayEntriesCount != 0) {
                dayAverageTemp /= dayEntriesCount
            }
            if (nightEntriesCount != 0) {
                nightAverageTemp /= nightEntriesCount
            }
            val formattedDayTemp = when (unitType) {
                UnitType.STANDARD -> resources.getString(
                    R.string.temp_value_default, dayAverageTemp
                )
                UnitType.METRIC -> resources.getString(
                    R.string.temp_value_metric, dayAverageTemp
                )
                UnitType.IMPERIAL -> resources.getString(
                    R.string.temp_value_imperial, dayAverageTemp
                )
            }
            result.add(
                FormattedForecast(when (day.dayOfWeek) {
                    DayOfWeek.MONDAY -> resources.getString(R.string.monday)
                    DayOfWeek.TUESDAY -> resources.getString(R.string.tuesday)
                    DayOfWeek.WEDNESDAY -> resources.getString(R.string.wednesday)
                    DayOfWeek.THURSDAY -> resources.getString(R.string.thursday)
                    DayOfWeek.FRIDAY -> resources.getString(R.string.friday)
                    DayOfWeek.SATURDAY -> resources.getString(R.string.saturday)
                    DayOfWeek.SUNDAY -> resources.getString(R.string.sunday)
                    else -> resources.getString(R.string.placeholder)
                },
                    "$nightAverageTemp/$formattedDayTemp",
                    dailyForecast[day]!!.map { getFormattedWeather(it, unitType) })
            )
        }
        return result
    }

    fun getFormattedWeather(
        weather: Weather, unitType: UnitType
    ): FormattedWeather = FormattedWeather(
        WeatherCondition.fromIconId(if (weather.weatherIconId != DefaultValues.STRING) weather.weatherIconId else "00x").iconDrawable,
        if (weather.weatherDescription != DefaultValues.STRING) weather.weatherDescription else resources.getString(
            R.string.placeholder
        ),
        when (unitType) {
            UnitType.STANDARD -> resources.getString(
                R.string.temp_value_default, weather.temperature.toInt()
            )
            UnitType.METRIC -> resources.getString(
                R.string.temp_value_metric, weather.temperature.toInt()
            )
            UnitType.IMPERIAL -> resources.getString(
                R.string.temp_value_imperial, weather.temperature.toInt()
            )
        },
        if (weather.timeStamp != DefaultValues.TIME) weather.timeStamp.plusSeconds(OffsetDateTime.now().offset.totalSeconds.toLong())
            .format(
                DateTimeFormatter.ofPattern("HH:mm dd.MM")
            )
        else resources.getString(
            R.string.placeholder
        ),
        getParametersList(weather, unitType)
    )

    private fun getParametersList(
        weather: Weather, unitType: UnitType
    ): List<WeatherParameter> {
        val result = mutableListOf<WeatherParameter>()
        if (weather.temperatureFeelsLike != (DefaultValues.DOUBLE as Number)) {
            when (unitType) {
                UnitType.STANDARD -> result.add(
                    WeatherParameter(
                        resources.getString(R.string.feels_like),
                        R.drawable.thermometer,
                        resources.getString(
                            R.string.temp_value_default, weather.temperatureFeelsLike.toInt()
                        )
                    )
                )
                UnitType.METRIC -> result.add(
                    WeatherParameter(
                        resources.getString(R.string.feels_like),
                        R.drawable.thermometer,
                        resources.getString(
                            R.string.temp_value_metric, weather.temperatureFeelsLike.toInt()
                        )
                    )
                )
                UnitType.IMPERIAL -> result.add(
                    WeatherParameter(
                        resources.getString(R.string.feels_like),
                        R.drawable.thermometer,
                        resources.getString(
                            R.string.temp_value_imperial, weather.temperatureFeelsLike.toInt()
                        )
                    )
                )
            }
        }
        if (weather.pressure != (DefaultValues.DOUBLE as Number)) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.pressure),
                    R.drawable.pressure,
                    resources.getString(
                        R.string.pressure_value, weather.pressure.toInt()
                    )
                )
            )
        }
        if (weather.humidity != DefaultValues.INT) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.humidity),
                    R.drawable.humidity,
                    "${weather.humidity} %"
                )
            )
        }
        if (weather.visibility != DefaultValues.INT) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.visibility),
                    R.drawable.visibility,
                    resources.getString(
                        R.string.visibility_value, weather.visibility.toDouble() / 1000
                    )
                )
            )
        }
        if (weather.windSpeed != (DefaultValues.DOUBLE as Number)) {
            when (unitType) {
                UnitType.IMPERIAL -> result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_speed),
                        R.drawable.wind,
                        resources.getString(
                            R.string.wind_speed_value_imperial, weather.windSpeed
                        )
                    )
                )
                else -> result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_speed),
                        R.drawable.wind,
                        resources.getString(
                            R.string.wind_speed_value_default_metric, weather.windSpeed
                        )
                    )
                )
            }
        }
        if (weather.windDirectionDegree != DefaultValues.INT) {
            if (weather.windDirectionDegree >= 337.5 || weather.windDirectionDegree < 22.5) {
                result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_direction),
                        R.drawable.winddirection,
                        resources.getString(R.string.wind_dir_north)
                    )
                )
            } else if (weather.windDirectionDegree >= 22.5 && weather.windDirectionDegree < 67.5) {
                result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_direction),
                        R.drawable.winddirection,
                        resources.getString(R.string.wind_dir_ne)
                    )
                )
            } else if (weather.windDirectionDegree >= 67.5 && weather.windDirectionDegree < 112.5) {
                result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_direction),
                        R.drawable.winddirection,
                        resources.getString(R.string.wind_dir_east)
                    )
                )
            } else if (weather.windDirectionDegree >= 112.5 && weather.windDirectionDegree < 157.5) {
                result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_direction),
                        R.drawable.winddirection,
                        resources.getString(R.string.wind_dir_se)
                    )
                )
            } else if (weather.windDirectionDegree >= 157.5 && weather.windDirectionDegree < 202.5) {
                result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_direction),
                        R.drawable.winddirection,
                        resources.getString(R.string.wind_dir_south)
                    )
                )
            } else if (weather.windDirectionDegree >= 202.5 && weather.windDirectionDegree < 247.5) {
                result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_direction),
                        R.drawable.winddirection,
                        resources.getString(R.string.wind_dir_sw)
                    )
                )
            } else if (weather.windDirectionDegree >= 247.5 && weather.windDirectionDegree < 292.5) {
                result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_direction),
                        R.drawable.winddirection,
                        resources.getString(R.string.wind_dir_west)
                    )
                )
            } else if (weather.windDirectionDegree >= 292.5 && weather.windDirectionDegree < 337.5) {
                result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_direction),
                        R.drawable.winddirection,
                        resources.getString(R.string.wind_dir_nw)
                    )
                )
            }
        }
        if (weather.windGustSpeed != (DefaultValues.DOUBLE as Number)) {
            when (unitType) {
                UnitType.IMPERIAL -> result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_gust),
                        R.drawable.wind,
                        resources.getString(
                            R.string.wind_speed_value_imperial, weather.windGustSpeed
                        )
                    )
                )
                else -> result.add(
                    WeatherParameter(
                        resources.getString(R.string.wind_gust),
                        R.drawable.wind,
                        resources.getString(
                            R.string.wind_speed_value_default_metric, weather.windGustSpeed
                        )
                    )
                )
            }
        }
        if (weather.cloudiness != DefaultValues.INT) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.cloudiness),
                    R.drawable.cloudy,
                    "${weather.cloudiness} %"
                )
            )
        }
        if (weather.rainVolumeHour != (DefaultValues.DOUBLE as Number)) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.rain_volume_hour),
                    R.drawable.rain,
                    resources.getString(
                        R.string.precipitation_volume_value, weather.rainVolumeHour
                    )
                )
            )
        }
        if (weather.rainVolumeThreeHours != (DefaultValues.DOUBLE as Number)) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.rain_volume_3h),
                    R.drawable.rain,
                    resources.getString(
                        R.string.precipitation_volume_value, weather.rainVolumeThreeHours
                    )
                )
            )
        }
        if (weather.snowVolumeHour != (DefaultValues.DOUBLE as Number)) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.snow_volume_hour),
                    R.drawable.snow,
                    resources.getString(
                        R.string.precipitation_volume_value, weather.snowVolumeHour
                    )
                )
            )
        }
        if (weather.snowVolumeThreeHours != (DefaultValues.DOUBLE as Number)) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.snow_volume_3h),
                    R.drawable.snow,
                    resources.getString(
                        R.string.precipitation_volume_value, weather.snowVolumeThreeHours
                    )
                )
            )
        }
        if (weather.sunriseTime != DefaultValues.TIME) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.sunrise_time),
                    R.drawable.clear_day,
                    weather.sunriseTime.plusSeconds(OffsetDateTime.now().offset.totalSeconds.toLong())
                        .format(
                            DateTimeFormatter.ofPattern("HH:mm")
                        )
                )
            )
        }
        if (weather.sunsetTime != DefaultValues.TIME) {
            result.add(
                WeatherParameter(
                    resources.getString(R.string.sunset_time),
                    R.drawable.clear_night,
                    weather.sunsetTime.plusSeconds(OffsetDateTime.now().offset.totalSeconds.toLong())
                        .format(
                            DateTimeFormatter.ofPattern("HH:mm")
                        )
                )
            )
        }
        return result
    }
}