package ru.cft.shift2023winter.data.datasource

import ru.cft.shift2023winter.domain.entity.*

interface SettingsDataSource {
    suspend fun saveSettings(settingsInfo: SettingsInfo)

    suspend fun getSettings(): SettingsInfo
}