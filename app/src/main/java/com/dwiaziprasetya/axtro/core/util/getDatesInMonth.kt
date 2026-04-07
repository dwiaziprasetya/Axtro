package com.dwiaziprasetya.axtro.core.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
fun getDatesInMonth(yearMonth: YearMonth): List<LocalDate?> {
    val firstDayOfMonth = yearMonth.atDay(1)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

    val dates = mutableListOf<LocalDate?>()

    val prevMonth = yearMonth.minusMonths(1)
    for (i in firstDayOfWeek - 1 downTo 0) {
        dates.add(prevMonth.atDay(prevMonth.lengthOfMonth() - i))
    }

    for (i in 1..daysInMonth) {
        dates.add(yearMonth.atDay(i))
    }

    val remaining = 42 - dates.size
    for (i in 1..remaining) {
        dates.add(yearMonth.plusMonths(1).atDay(i))
    }

    return dates
}