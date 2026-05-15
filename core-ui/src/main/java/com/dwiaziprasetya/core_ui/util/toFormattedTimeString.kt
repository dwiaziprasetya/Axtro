package com.dwiaziprasetya.core_ui.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toFormattedTimeString(): String {
    val instant = Instant.ofEpochMilli(this)

    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

    return instant.atZone(ZoneId.systemDefault()).format(formatter)
}