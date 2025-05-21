package ru.practicum.android.diploma.common.domain.models

import ru.practicum.android.diploma.R

enum class Currency(
    val abbr: String,
    val abbrVariant: String? = null,
    val code: String? = null,
    val default: Boolean = false,
    val currencyName: String,
    val symbol: String
) {
    RUSSIAN_RUBLE(
        "RUB",
        abbrVariant = "RUR",
        default = true,
        currencyName = "Российский рубль",
        symbol = '\u20BD'.toString()
    ),
    BELARUSIAN_RUBLE("BYR", currencyName = "Белорусский рубль", symbol = "Br"),
    US_DOLLAR("USD", currencyName = "Доллар", symbol = '\u0024'.toString()),
    EURO("EUR", currencyName = "Евро", symbol = '\u20AC'.toString()),
    KAZAKHSTANI_TENGE("KZT", currencyName = "Казахстанский тенге", symbol = '\u20B8'.toString()),
    UKRAINIAN_HRYVNIA("UAH", currencyName = "Украинская гривна", symbol = '\u20B4'.toString()),
    AZERBAIJANI_MANAT("AZN", currencyName = "Азербайджанский манат", symbol = '\u20BC'.toString()),
    UZBEKISTANI_SOM("UZS", currencyName = "Узбекский сум", symbol = "so'm"),
    GEORGIAN_LARI("GEL", currencyName = "Грузинский лари", symbol = '\u20BE'.toString()),
    KYRGYZSTANI_SOM("KGS", currencyName = "Киргизский сом", symbol = "сом");

    companion object {
        fun currencyFromAbbr(abbr: String): Currency? {
            return entries.find { it.abbr == abbr } ?: RUSSIAN_RUBLE
        }
    }
}
