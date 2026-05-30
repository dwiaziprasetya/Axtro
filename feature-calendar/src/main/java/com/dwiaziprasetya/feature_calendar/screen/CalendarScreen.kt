package com.dwiaziprasetya.feature_calendar.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dwiaziprasetya.feature_calendar.component.HorizontalCalendar
import com.dwiaziprasetya.feature_calendar.component.TaskTimelineRow
import com.dwiaziprasetya.feature_calendar.model.CalendarDay
import com.dwiaziprasetya.feature_calendar.viewmodel.CalendarViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.Instant
import java.time.ZoneId
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier ,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val selectedDate = uiState.selectedDate
    val daysList = remember(selectedDate) {
        val currentMonth = YearMonth.from(selectedDate)
        val firstDayOfMonth = currentMonth.atDay(1)
        val totalDaysInMonth = currentMonth.lengthOfMonth()

        (0 until totalDaysInMonth).map { dayOffset ->
            val targetDate = firstDayOfMonth.plusDays(dayOffset.toLong())
            CalendarDay(
                date = targetDate,
                isSelected = targetDate == selectedDate
            )
        }
    }

    // Ubah status bar menjadi transparan dengan ikon putih karena background atasnya gelap/biru
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false // Ikon putih agar terbaca di atas background biru
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    // Background utama paling belakang menggunakan warna primary aplikasi kamu
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 1. AREA HEADER ATAS (Biru Gradasi + Judul + Kalender Horizontal)
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(bottom = 24.dp) // Jarak ke lengkungan bawah
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Judul Halaman ala Task Screen
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Calendar",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    val totalTasks = uiState.tasks.size
                    Text(
                        text = if (totalTasks > 0) "$totalTasks tasks scheduled for this day" else "No tasks today",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Kalender horizontal disisipkan di dalam area biru ini
                HorizontalCalendar(
                    currentMonth = YearMonth.from(selectedDate),
                    days = daysList,
                    onDaySelected = { viewModel.changeSelectedDate(it.date) },
                    onMonthChanged = { newMonth ->
                        viewModel.changeSelectedDate(newMonth.atDay(1))
                    }
                )
            }

            // 2. AREA KONTEN UTAMA (Container Putih Melengkung ke Atas)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.background, // Warna putih/abu latar belakang bawaanmu
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp) // Lengkungan khas ala image_4ab22a.jpg
                    )
                    .padding(top = 24.dp) // Jarak dari batas atas lengkungan ke list item pertama
            ) {
                when {
                    uiState.isLoading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    uiState.error != null -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Error: ${uiState.error}", color = Color.Red)
                        }
                    }
                    else -> {
                        LazyColumn(
                            modifier = modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 120.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(24) { hour ->
                                val tasksForThisHour = uiState.tasks.filter { task ->
                                    val taskHour = Instant.ofEpochMilli(task.startTime)
                                        .atZone(ZoneId.systemDefault())
                                        .hour
                                    taskHour == hour
                                }

                                TaskTimelineRow(
                                    hour = hour,
                                    tasks = tasksForThisHour,
                                    modifier = Modifier.height(IntrinsicSize.Max)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}