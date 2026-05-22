package com.dwiaziprasetya.feature_task.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dwiaziprasetya.feature_task.model.SortType

@Composable
fun FilterAndSortBottomSheet(
    selectedSort: SortType,
    onClose: () -> Unit,
    onResetAll: () -> Unit,
    onApply: (SortType) -> Unit
) {
    val sortOptions = SortType.entries
    var tempSelectedSort by remember {
        mutableStateOf(selectedSort)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = 32.dp
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Close ,
                contentDescription = "Close",
                modifier = Modifier.clickable { onClose() }
            )
            Box(
                modifier = Modifier.weight(1f) ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sort By" ,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onResetAll() }
            ) {
                Text(
                    text = "Reset All" ,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ) ,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Check ,
                    contentDescription = "Apply" ,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(sortOptions) { option ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            tempSelectedSort = option
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = option.title,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    RadioButton(
                        selected = tempSelectedSort == option,
                        onClick = {
                            tempSelectedSort = option
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            onClick = { onApply(tempSelectedSort) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Confirm",
                style = MaterialTheme.typography.labelLarge.copy(
                    Color.White
                )
            )
        }
    }
}