package ru.practicum.android.diploma.common.domain.models

enum class Currency(
    val abbr: String,
    val abbrVariant: String? = null,
    val code: String? = null,
    val default: Boolean = false,
    val currencyName: String,
) {
    RUSSIAN_RUBLE("RUB", abbrVariant = "RUR", default = true, currencyName = "Российский рубль"),
    BELARUSIAN_RUBLE("BYR", currencyName = "Белорусский рубль"),
    US_DOLLAR("USD", currencyName = "Доллар"),
    EURO("EUR", currencyName = "Евро"),
    KAZAKHSTANI_TENGE("KZT", currencyName = "Казахстанский тенге"),
    UKRAINIAN_HRYVNIA("UAH", currencyName = "Украинская гривна"),
    AZERBAIJANI_MANAT("AZN", currencyName = "Азербайджанский манат"),
    UZBEKISTANI_SOM("UZS", currencyName = "Узбекский сум"),
    GEORGIAN_LARI("GEL", currencyName = "Грузинский лари"),
    KYRGYZSTANI_SOM("KGS", currencyName = "Киргизский сом")
}
