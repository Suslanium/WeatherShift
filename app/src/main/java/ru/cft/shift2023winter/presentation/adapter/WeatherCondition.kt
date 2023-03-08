package ru.cft.shift2023winter.presentation.adapter

import androidx.annotation.DrawableRes
import ru.cft.shift2023winter.R

enum class WeatherCondition(val iconIds: List<String>, @DrawableRes val iconDrawable: Int) {
    UNKNOWN(listOf("00x"), R.drawable.question_mark),
    CLEAR_DAY(listOf("01d"), R.drawable.clear_day),
    CLEAR_NIGHT(listOf("01n"), R.drawable.clear_night),
    PARTLY_CLOUDY_DAY(listOf("02d"), R.drawable.partly_cloudy_day),
    PARTLY_CLOUDY_NIGHT(listOf("02n"), R.drawable.partly_cloudy_night),
    CLOUDY(listOf("03n", "03d", "04d", "04n"), R.drawable.cloudy),
    RAINY(listOf("09d", "09n", "10d", "10n"), R.drawable.rainy),
    THUNDERSTORM(listOf("11d", "11n"), R.drawable.thunderstorm),
    SNOW(listOf("13d", "13n"), R.drawable.snow),
    FOGGY(listOf("50d", "50n"), R.drawable.foggy);

    companion object {
        fun fromIconId(iconId: String) = WeatherCondition.values().first { weatherCondition ->
            try {
                weatherCondition.iconIds.first { currentId -> currentId == iconId }
                true
            } catch (ex: Exception) {
                false
            }
        }
    }
}