package com.dwiaziprasetya.feature_calendar.model

import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate ,
    val isSelected: Boolean = false
)