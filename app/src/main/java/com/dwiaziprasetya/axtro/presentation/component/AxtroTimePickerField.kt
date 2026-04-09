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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiaziprasetya.axtro.R

@Composable
fun AxtroTimePickerField(
    modifier: Modifier = Modifier,
    label: String = "HH:mm",
    time: String,
    onTimePickerClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTimePickerClick() }
    ) {
        OutlinedTextField(
            value = time,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                textDecoration = TextDecoration.None
            ),
            placeholder =  {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.outline,
                    fontSize = 14.sp,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_clock),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = if (time == "HH:mm") Color.Gray else Color.Black,
                disabledContainerColor = Color.White,
                disabledBorderColor = Color.Transparent,
                disabledPlaceholderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
}