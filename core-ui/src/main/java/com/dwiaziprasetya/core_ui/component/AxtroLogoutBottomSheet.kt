package com.dwiaziprasetya.core_ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiaziprasetya.axtro.presentation.home.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutBottomSheet(
    state: HomeUiState,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onCancel,
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            Text(
                text = "Logout",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Are you sure you want to logout?",
            )

            Spacer(Modifier.height(24.dp))

            Row {

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onCancel
                ) {
                    Text(
                        "Cancel",
                        color = Color.White
                    )
                }

                Spacer(Modifier.width(12.dp))

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onConfirm
                ) {

                    if (state.isLogoutLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    } else {
                        Text(
                            "Yes, Logout",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}