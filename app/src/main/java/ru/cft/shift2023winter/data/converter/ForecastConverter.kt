package ru.cft.shift2023winter.data.converter

import ru.cft.shift2023winter.DefaultValues
import ru.cft.shift2023winter.data.model.forecast.ForecastModel
import ru.cft.shift2023winter.domain.entity.Forecast
import ru.cft.shift2023winter.domain.entity.Weather

class ForecastConverter {
    fun convert(from: ForecastModel) = Forecast(from.list.map {
        Weather(
            it.weather?.get(0)?.description ?: DefaultValues.STRING,
            it.weather?.get(0)?.icon ?: DefaultValues.STRING,
            it.main?.temp ?: DefaultValues.DOUBLE,
            it.main?.feels_like ?: DefaultValues.DOUBLE,
            it.main?.pressure ?: DefaultValues.DOUBLE,
            it.main?.humidity ?: DefaultValues.INT,
            it.main?.temp_min ?: DefaultValues.DOUBLE,
            it.main?.temp_max ?: DefaultValues.DOUBLE,
            it.visibility ?: DefaultValues.INT,
            it.wind?.speed ?: DefaultValues.DOUBLE,
            it.wind?.deg ?: DefaultValues.INT,
            it.wind?.gust ?: DefaultValues.DOUBLE,
            it.clouds?.all ?: DefaultValues.INT,
            it.rain?.hour ?: DefaultValues.DOUBLE,
            it.rain?.threeHours ?: DefaultValues.DOUBLE,
            it.snow?.hour ?: DefaultValues.DOUBLE,
            it.snow?.threeHours ?: DefaultValues.DOUBLE,
            from.city?.sunrise ?: DefaultValues.TIME,
            from.city?.sunset ?: DefaultValues.TIME,
            from.city?.timezone ?: DefaultValues.LONG,
            it.dt ?: DefaultValues.TIME
        )
    })
}