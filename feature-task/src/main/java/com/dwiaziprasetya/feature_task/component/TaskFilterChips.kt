package com.dwiaziprasetya.feature_task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun TaskFilterChips(
    filters: List<String> = listOf("All", "Active", "Completed"),
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val selectedIndex = filters.indexOf(selectedFilter).coerceAtLeast(0)

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        divider = {},
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .fillMaxSize()
                    .padding(4.dp)
                    .zIndex(-1f)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        filters.forEachIndexed { index, filter ->
            val isSelected = selectedIndex == index
            Tab(
                selected = isSelected,
                onClick = { onFilterSelected(filter) },
                interactionSource = object : MutableInteractionSource {
                    override val interactions: Flow<Interaction> = emptyFlow()
                    override suspend fun emit(interaction: Interaction) {}
                    override fun tryEmit(interaction: Interaction) = true
                },
                text = {
                    Text(
                        text = filter,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isSelected) Color.White else Color.Gray
                    )
                }
            )
        }
    }
}