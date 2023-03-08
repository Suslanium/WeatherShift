package ru.cft.shift2023winter.data.model.location

import ru.cft.shift2023winter.DefaultValues

data class LocationModel(
    val name: String= DefaultValues.STRING,
    val local_names: HashMap<String,String> = hashMapOf(),
    val lat: Double = DefaultValues.DOUBLE,
    val lon: Double = DefaultValues.DOUBLE,
    val country: String = DefaultValues.STRING,
    val state: String = DefaultValues.STRING
)
