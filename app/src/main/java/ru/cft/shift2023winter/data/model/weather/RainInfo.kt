package ru.cft.shift2023winter.data.model.weather

import com.google.gson.annotations.SerializedName
import ru.cft.shift2023winter.data.DefaultValues

data class RainInfo(
    @field:SerializedName("1h")
    val hour: Double = DefaultValues.DOUBLE,
    @field:SerializedName("3h")
    val threeHours: Double = DefaultValues.DOUBLE
)
