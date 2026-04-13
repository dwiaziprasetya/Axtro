package com.dwiaziprasetya.axtro.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AxtroLabeledTextField(
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    isSingleLine: Boolean = true,
    trailingIconResId: Int? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.height(4.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            singleLine = isSingleLine,
            value = text,
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(10.dp),
            onValueChange = { onTextChange(it) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
            placeholder =  {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = if (trailingIconResId != null) {
                {
                    Icon(
                        painter = painterResource(trailingIconResId),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                    )
                }
            } else null
        )
    }
}