package com.dwiaziprasetya.axtro.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwiaziprasetya.axtro.R

@Composable
fun AxtroDatePickerField(
    modifier: Modifier = Modifier,
    label: String = "Select date",
    selectedDate: String,
    onDateSelected: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onDateSelected() }
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            placeholder = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_calendar_outlined),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = if (selectedDate == "DD/MM/YYYY") Color.Gray else Color.Black,
                disabledContainerColor = Color.White,
                disabledBorderColor = Color.Transparent,
                disabledPlaceholderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
}