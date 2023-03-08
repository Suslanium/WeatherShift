package ru.cft.shift2023winter.data.converter

import ru.cft.shift2023winter.DefaultValues
import ru.cft.shift2023winter.data.model.weather.WeatherModel
import ru.cft.shift2023winter.domain.entity.Weather

class WeatherConverter {
    fun convert(from: WeatherModel) = Weather(
        from.weather?.get(0)?.description ?: DefaultValues.STRING,
        from.weather?.get(0)?.icon ?: DefaultValues.STRING,
        from.main?.temp ?: DefaultValues.DOUBLE,
        from.main?.feels_like ?: DefaultValues.DOUBLE,
        from.main?.pressure ?: DefaultValues.DOUBLE,
        from.main?.humidity ?: DefaultValues.INT,
        from.main?.temp_min ?: DefaultValues.DOUBLE,
        from.main?.temp_max ?: DefaultValues.DOUBLE,
        from.visibility ?: DefaultValues.INT,
        from.wind?.speed ?: DefaultValues.DOUBLE,
        from.wind?.deg ?: DefaultValues.INT,
        from.wind?.gust ?: DefaultValues.DOUBLE,
        from.clouds?.all ?: DefaultValues.INT,
        from.rain?.hour ?: DefaultValues.DOUBLE,
        from.rain?.threeHours ?: DefaultValues.DOUBLE,
        from.snow?.hour ?: DefaultValues.DOUBLE,
        from.snow?.threeHours ?: DefaultValues.DOUBLE,
        from.sys?.sunrise ?: DefaultValues.TIME,
        from.sys?.sunset ?: DefaultValues.TIME,
        from.timezone,
        from.dt ?: DefaultValues.TIME
    )
}