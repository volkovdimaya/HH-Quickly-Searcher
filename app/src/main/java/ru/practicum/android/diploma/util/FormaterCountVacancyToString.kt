package ru.practicum.android.diploma.util

object FormatterCountVacancyToString {
    operator fun invoke(count: Int): String {
        val lastDigit = count % 10
        val lastTwoDigits = count % 100

        return when {
            lastDigit == 1 && lastTwoDigits != 11 -> "Найдена $count вакансия"
            lastDigit in 2..4 && lastTwoDigits !in 12..14 -> "Найдено $count вакансии"
            else -> "Найдено $count вакансий"
        }
    }
}
