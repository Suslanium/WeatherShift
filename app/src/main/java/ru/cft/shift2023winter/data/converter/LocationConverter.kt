package ru.cft.shift2023winter.data.converter

import ru.cft.shift2023winter.data.model.location.LocationModel
import ru.cft.shift2023winter.domain.entity.Location
import ru.cft.shift2023winter.domain.entity.MapCoordinates

class LocationConverter {
    fun convert(from: LocationModel) : Location = Location(
        from.name,
        from.local_names,
        from.country,
        from.state,
        MapCoordinates(from.lat,from.lon)
    )
}