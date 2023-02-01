package ru.cft.shift2023winter.domain.usecase

import ru.cft.shift2023winter.domain.entity.SettingsInfo
import ru.cft.shift2023winter.domain.repository.SettingsRepository

class GetSettingsUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(): SettingsInfo = settingsRepository.getSettings()
}