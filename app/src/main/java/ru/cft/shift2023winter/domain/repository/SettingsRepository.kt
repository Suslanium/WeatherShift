package ru.cft.shift2023winter.domain.repository

import ru.cft.shift2023winter.domain.entity.*

interface SettingsRepository {
    suspend fun setSettings(settingsInfo: SettingsInfo)

    suspend fun getSettings(): SettingsInfo
}