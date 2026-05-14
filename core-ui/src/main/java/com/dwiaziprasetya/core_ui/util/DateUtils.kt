package com.dwiaziprasetya.core_ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun formatDate(timestamp: Long): String {

        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        return formatter.format(Date(timestamp))
    }
}