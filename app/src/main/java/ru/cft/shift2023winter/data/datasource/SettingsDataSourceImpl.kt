package ru.cft.shift2023winter.data.datasource

import android.content.res.Resources
import android.util.Log
import com.google.gson.Gson
import ru.cft.shift2023winter.domain.entity.LanguageCode
import ru.cft.shift2023winter.domain.entity.SettingsInfo
import ru.cft.shift2023winter.domain.entity.UnitType
import ru.cft.shift2023winter.domain.entity.UpdateInterval
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class SettingsDataSourceImpl(
    private val deserializerGson: Gson,
    private val serializerGson: Gson,
    fileDirectory: File
) : SettingsDataSource {
    companion object {
        private const val ERROR_TAG = "SettingsIO"
        private val defaultLanguageCode =
            Resources.getSystem().configuration.locales[0].language.uppercase()
        private val defaultSettings = SettingsInfo(
            null,
            if (LanguageCode.hasLanguage(defaultLanguageCode)) LanguageCode.valueOf(
                defaultLanguageCode
            ) else LanguageCode.EN,
            UnitType.METRIC,
            UpdateInterval.MANUAL_ONLY,
            false
        )
    }

    private val settingsFile = File(fileDirectory.path + File.separator + "Settings.json")

    override suspend fun saveSettings(settingsInfo: SettingsInfo) {
        try {
            settingsFile.delete()
            val settingsWriter = FileWriter(settingsFile)
            serializerGson.toJson(settingsInfo, settingsWriter)
            settingsWriter.flush()
            settingsWriter.close()
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.stackTraceToString())
        }
    }

    override suspend fun getSettings(): SettingsInfo {
        try {
            if (settingsFile.exists()) {
                val settingsReader = FileReader(settingsFile)
                val result = deserializerGson.fromJson(settingsReader, SettingsInfo::class.java)
                settingsReader.close()
                return result
            }
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.stackTraceToString())
        }
        return defaultSettings
    }
}