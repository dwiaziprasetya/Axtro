package com.example.axtro.presentation.addTask

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axtro.core.ui.theme.ListifyTheme
import com.example.axtro.core.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                title = {
                    Text(
                        text = "Add Task",
                        fontFamily = poppinsFontFamily,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                     )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
private fun AddTaskScreenPreview() {
    ListifyTheme {
        AddTaskScreen()
    }
}