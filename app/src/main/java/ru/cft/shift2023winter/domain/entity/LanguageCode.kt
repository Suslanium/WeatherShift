package ru.cft.shift2023winter.domain.entity

enum class LanguageCode {
    EN,
    RU;

    companion object {
        fun hasLanguage(code: String): Boolean {
            return LanguageCode.values().any { it.name == code }
        }
    }
}