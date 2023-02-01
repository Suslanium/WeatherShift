package ru.cft.shift2023winter.data.repository

import ru.cft.shift2023winter.data.datasource.SettingsDataSource
import ru.cft.shift2023winter.domain.entity.SettingsInfo
import ru.cft.shift2023winter.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val settingsDataSource: SettingsDataSource) :
    SettingsRepository {
    override suspend fun setSettings(settingsInfo: SettingsInfo) =
        settingsDataSource.saveSettings(settingsInfo)

    override suspend fun getSettings(): SettingsInfo = settingsDataSource.getSettings()
}