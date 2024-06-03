package com.coroutines.data.models

import com.coroutines.common.preferences.EnumPreference
import com.coroutines.common.preferences.key

enum class LangEnum(val langId: String, val langName: String, val langNativeName: String) : EnumPreference by key("language") {
    ENGLISH(langId = "en", langName = "English", langNativeName = "English") { override val isDefault = true },
    ARABIC(langId = "ar", langName = "Arabic", langNativeName = "عربي"),
    FRENCH(langId = "fr", langName = "French", langNativeName = "Français"),
    ITALIAN(langId = "it", langName = "Italian", langNativeName = "Italiano"),
    GERMAN(langId = "de", langName = "German", langNativeName = "Deutsch"),
    PORTUGUESE(langId = "pt", langName = "Portuguese", langNativeName = "Português"),
    RUSSIAN(langId = "ru", langName = "Russian", langNativeName = "Русский"),
    SPANISH(langId = "es", langName = "Spanish", langNativeName = "Español"),
    SWEDISH(langId = "sv", langName = "Swedish", langNativeName = "svenska"),

    ;

    companion object {
        infix fun from(value: String): LangEnum? {
            return LangEnum.values().firstOrNull { it.langId == value }
        }
    }
}

interface ILanguageSelection {
    fun langEnumId(): LangEnum
    fun langName(): String
}

sealed class LangSelection : ILanguageSelection {
    override fun equals(other: Any?): Boolean =
        other is LangSelection && other.langEnumId() == langEnumId()

    override fun hashCode(): Int = langEnumId().hashCode()
    class EnglishLanguage : LangSelection() {
        override fun equals(other: Any?): Boolean =
            other is EnglishLanguage && other.langEnumId() == langEnumId()

        override fun langEnumId(): LangEnum = LangEnum.ENGLISH
        override fun langName(): String = "English"
        override fun hashCode(): Int = langEnumId().hashCode()
    }

    class ArabicLanguage : LangSelection() {
        override fun equals(other: Any?): Boolean =
            other is ArabicLanguage && other.langEnumId() == langEnumId()

        override fun langEnumId(): LangEnum = LangEnum.ARABIC
        override fun langName(): String = "Arabic"
        override fun hashCode(): Int = langEnumId().hashCode()
    }

    class GermanLanguage : LangSelection() {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun langEnumId(): LangEnum = LangEnum.GERMAN
        override fun langName(): String = "German"
        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class SwedishLanguage : LangSelection() {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun langEnumId(): LangEnum = LangEnum.SWEDISH
        override fun langName(): String = "Swedish"
        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class RussianLanguage : LangSelection() {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun langEnumId(): LangEnum = LangEnum.RUSSIAN
        override fun langName(): String = "Russian"
        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class FrenchLanguage : LangSelection() {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun langEnumId(): LangEnum = LangEnum.FRENCH
        override fun langName(): String = "French"
        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class ItalianLanguage : LangSelection() {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun langEnumId(): LangEnum = LangEnum.ITALIAN
        override fun langName(): String = "Italian"
        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class PortugueseLanguage : LangSelection() {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun langEnumId(): LangEnum = LangEnum.PORTUGUESE
        override fun langName(): String = "Português"
        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class SpanishLanguage : LangSelection() {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun langEnumId(): LangEnum = LangEnum.SPANISH
        override fun langName(): String = "Spanish"
        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    companion object {
        fun getLangById(langEnum: LangEnum): LangSelection {
            return when (langEnum) {
                LangEnum.ENGLISH -> EnglishLanguage()
                LangEnum.ARABIC -> ArabicLanguage()
                LangEnum.FRENCH -> FrenchLanguage()
                LangEnum.ITALIAN -> ItalianLanguage()
                LangEnum.GERMAN -> GermanLanguage()
                LangEnum.PORTUGUESE -> PortugueseLanguage()
                LangEnum.RUSSIAN -> RussianLanguage()
                LangEnum.SPANISH -> SpanishLanguage()
                LangEnum.SWEDISH -> SwedishLanguage()
            }
        }
    }
}