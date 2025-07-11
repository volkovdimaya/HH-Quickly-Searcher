package ru.practicum.android.diploma.util

object FormatterCountVacancyToString {
    private const val LAST_DIGIT_MODULO = 10
    private const val LAST_TWO_DIGITS_MODULO = 100
    private const val EXCEPTIONAL_LAST_TWO_DIGITS = 11
    private const val ONE_LAST_DIGIT = 1
    private const val EXCEPTIONAL_LAST_TWO_DIGITS_START = 12
    private const val EXCEPTIONAL_LAST_TWO_DIGITS_END = 14
    private const val FEW_LAST_DIGITS_START = 2
    private const val FEW_LAST_DIGITS_END = 4
    operator fun invoke(count: Int): String {
        val lastDigit = count % LAST_DIGIT_MODULO
        val lastTwoDigits = count % LAST_TWO_DIGITS_MODULO

        return when {
            lastDigit == ONE_LAST_DIGIT && lastTwoDigits != EXCEPTIONAL_LAST_TWO_DIGITS -> "Найдена $count вакансия"
            lastDigit in FEW_LAST_DIGITS_START..FEW_LAST_DIGITS_END
                && lastTwoDigits !in EXCEPTIONAL_LAST_TWO_DIGITS_START..EXCEPTIONAL_LAST_TWO_DIGITS_END ->
                "Найдено $count вакансии"

            else -> "Найдено $count вакансий"
        }
    }

}
