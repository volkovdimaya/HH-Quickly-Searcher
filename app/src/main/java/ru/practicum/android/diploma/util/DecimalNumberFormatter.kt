package ru.practicum.android.diploma.util

import java.text.DecimalFormat

object DecimalNumberFormatter {

    fun formatNumber(number: Int): String {
        val formatter = DecimalFormat("#,###")
        formatter.groupingSize = GROUPING_SIZE
        return formatter.format(number)
    }

    private const val GROUPING_SIZE = 3
}
