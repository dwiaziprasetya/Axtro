package com.example.listify.presentation.ui.screen.auth.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listify.presentation.theme.ListifyTheme

@Composable
fun SignInScreen() {
    SignInScreenContent()
}

@Composable
fun SignInScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.TopCenter)
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun SignInScreenContentPreview(
) {
    ListifyTheme(
        dynamicColor = false
    ) {
        SignInScreenContent()
    }
}