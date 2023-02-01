package ru.cft.shift2023winter.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZoneOffset

object LocalDateTimeDeserializer: JsonDeserializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        if (json != null) {
            return LocalDateTime.ofEpochSecond(json.asLong,0, ZoneOffset.UTC)
        }
        return LocalDateTime.ofEpochSecond(0,0, ZoneOffset.UTC)
    }
}