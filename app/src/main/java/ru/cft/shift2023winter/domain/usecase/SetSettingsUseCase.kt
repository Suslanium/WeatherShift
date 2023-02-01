package ru.cft.shift2023winter.domain.usecase

import ru.cft.shift2023winter.domain.entity.SettingsInfo
import ru.cft.shift2023winter.domain.repository.SettingsRepository

class SetSettingsUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(settingsInfo: SettingsInfo) =
        settingsRepository.setSettings(settingsInfo)
}