package com.coroutines.data.models

import com.coroutines.common.preferences.EnumPreference
import com.coroutines.common.preferences.key

enum class Languages(
    val languageId: String,
    val native: String,
    val appDescription: String,
) : EnumPreference by key("language") {
    ENGLISH("en", "English", LanguageAppDescription.English) { override val isDefault = true },
    RUSSIAN("ru", "Русский", LanguageAppDescription.Russian) { override val isDefault = false },
    GERMAN("de", "Deutsch", LanguageAppDescription.German),
    SWEDISH("sv", "Svenska", LanguageAppDescription.Swedish),
    PORTUGUESE("pt", "Português", LanguageAppDescription.Portuguese),
    SPANISH("es", "Español", LanguageAppDescription.Spanish),
    FRENCH("fr", "Française", LanguageAppDescription.French),
    ITALIAN(languageId = "it", native = "Italiano", LanguageAppDescription.English),
    ARABIC("ar", "عربى", LanguageAppDescription.Arabic),
    BOSNIAN("bs", "Bosanski", LanguageAppDescription.English),
    ;

    companion object {
        fun from(s: String): Languages? {
            return values().find { it.languageId == s }
        }
    }
}

@Suppress("MaxLineLength")
object LanguageAppDescription {
    const val English = "Discover what happened on this date throughout history. " +
            "All major events, births and deaths, across the world." +
            "\r\nThis app is available in multiple languages: " +
            "English, French, German, Arabic, Spanish, Portuguese, Russian, Bosnian and Swedish."
    const val Russian = "Что произошло в мире сегодня? Вчера? " +
            "Сто лет тому назад? Кто родился? Кто умер в этот день?\r\n" +
            "Календарь исторических событий и памятных дат в ваших руках. " +
            "Когда в России провели аграрную реформу, когда в США убили Джонна Кеннеди, " +
            "когда штурмовали Бастилию во Франции?\r\n" +
            " Все даты истории в одном приложении. " +
            "Чтобы посмотреть хронику событий на любой другой день года, кликайте нужную вам дату " +
            "и вы немедленно узнаете историю этого дня за последние два тысячелетия."
    const val Portuguese = "O que aconteceu no mundo hoje? Ontem? Cem anos atrás? Quem nasceu? " +
            "Quem morreu neste dia?\r\n" +
            "Calendário de eventos históricos e datas memoráveis \u200B\u200Bem suas mãos. " +
            "Quando ocorreu a reforma agrária na Rússia, quando John F. Kennedy foi assassinado nos EUA, " +
            "quando tomaram a Bastilha na França? Todas as datas do histórico em um único aplicativo." +
            " Para ver a crônica dos eventos de qualquer outro dia do ano, clique na data desejada " +
            "e você descobrirá imediatamente a história deste dia nos últimos dois milênios."

    const val Spanish = "¿Qué pasó en el mundo hoy? ¿El dia de ayer? ¿Hace cien años? ¿Quién nació? " +
            "¿Quién murió en este día?\r\n" +
            "Calendario de eventos históricos y fechas memorables en tus manos. " +
            "¿Cuándo tuvo lugar la reforma agraria en Rusia, cuando asesinaron a John F. Kennedy en EE.UU., " +
            "cuando tomaron la Bastilla en Francia? Todas las fechas históricas en una sola app. " +
            "Para ver la crónica de eventos de cualquier otro día del año, " +
            "haga clic en la fecha deseada e inmediatamente descubrirá la historia " +
            "de este día durante los últimos dos milenios."

    const val Swedish = "Vad hände i världen idag? I går? " +
            "För hundra år sedan? Vem föddes? Vem dog denna dag?\r\n" +
            "Kalender med historiska händelser och minnesvärda datum i dina händer. " +
            "När ägde jordbruksreformer rum i Ryssland, när John F. Kennedy mördades i USA, " +
            "när de intog Bastiljen i Frankrike? Alla historiska datum i en enda app."

    const val German = "Was ist heute in der Welt passiert? Gestern? Vor hundert Jahren? " +
            "Der geboren wurde? Wer starb an diesem Tag?\r\n" +
            "Kalender mit historischen Ereignissen und denkwürdigen Daten in Ihren Händen. " +
            "Wann gab es in Russland eine Agrarreform, " +
            "wann wurde John F. Kennedy in den USA ermordet, " +
            "wann wurde in Frankreich die Bastille eingenommen? " +
            "Alle historischen Daten in einer einzigen App."

    const val French = "Que s'est-il passé dans le monde aujourd'hui? " +
            "Hier? Il y a cent ans? Qui était né? Qui est mort ce jour-là ?\r\n" +
            "Calendrier des événements historiques et des dates mémorables entre vos mains. " +
            "Quand la réforme agraire a-t-elle eu lieu en Russie, " +
            "quand John F. Kennedy a été assassiné aux États-Unis, " +
            "quand ils ont pris la Bastille en France ? " +
            "Toutes les dates historiques dans une seule application."

    const val Arabic = "ماذا حدث في العالم اليوم؟ في الامس؟ قبل مئة سنة؟ الذي ولد؟ من مات في هذا اليوم؟\r\n" +
            "تقويم الأحداث التاريخية والتواريخ التي لا تنسى بين يديك. متى حدث الإصلاح الزراعي في روسيا ، عندما اغتيل جون ف. كينيدي في" +
            "الولايات المتحدة ، عندما استولوا على الباستيل في فرنسا؟ جميع التواريخ التاريخية في تطبيق واحد."
}