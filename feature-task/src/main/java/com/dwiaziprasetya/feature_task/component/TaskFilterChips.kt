package com.dwiaziprasetya.feature_task.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TaskFilterChips(
    filters: List<String> = listOf("All", "Active", "Completed"),
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth() ,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        filters.forEach { filter ->
            FilterChip(
                modifier = Modifier.weight(1f) ,
                selected = selectedFilter == filter ,
                onClick = { onFilterSelected(filter) } ,
                label = {
                    Text(
                        text = filter ,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.fillMaxWidth() ,
                        textAlign = TextAlign.Center
                    )
                } ,
                shape = RoundedCornerShape(10.dp) ,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.Transparent ,
                    selectedContainerColor = MaterialTheme.colorScheme.primary ,
                    selectedLabelColor = Color.White ,
                ) ,
                border = null
            )
        }
    }
}