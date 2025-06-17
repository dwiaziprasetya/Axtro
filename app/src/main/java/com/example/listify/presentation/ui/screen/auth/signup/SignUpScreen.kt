package com.example.listify.presentation.ui.screen.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listify.R
import com.example.listify.presentation.theme.ListifyTheme
import com.example.listify.presentation.theme.poppinsFontFamily
import com.example.listify.presentation.ui.component.CustomOutlinedTextField
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignUpScreen() {
    val systemUiController = rememberSystemUiController()

    var email by rememberSaveable { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var isEmailFocused by remember { mutableStateOf(false) }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        R.drawable.icon_visibility
    else
        R.drawable.icon_visibility_off

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }

    SignUpScreenContent(
        email = email,
        password = password,
        onEmailChange = {
            email = it
            if (!isEmailFocused) {
                emailError = false
            }
        },
        onEmailFocusChange = { isFocused ->
            isEmailFocused = isFocused
            if (!isFocused) {
                emailError = email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        },
        onPasswordChange = {
            password = it
            passwordError = password.contains(" ")
        },
        passwordVisibility = passwordVisibility,
        onPasswordVisibilityChange = { passwordVisibility = !passwordVisibility },
        passwordError = passwordError,
        emailError = emailError,
        iconPasswordVisibility = icon,
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onEmailFocusChange: (Boolean) -> Unit,
    onPasswordChange: (String) -> Unit,
    passwordVisibility: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    emailError: Boolean,
    iconPasswordVisibility: Int,
    passwordError: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.TopCenter)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .statusBarsPadding()
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(60.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Sign up to your\naccount",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 32.sp,
                lineHeight = 32.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Get started and list your task",
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
            )
            Spacer(Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Email",
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                        )
                        Spacer(Modifier.height(4.dp))
                        CustomOutlinedTextField(
                            value = email,
                            onValueChange = onEmailChange,
                            onFocusChange = onEmailFocusChange,
                            trailingIconResId = null,
                            onTrailingIconClick = {},
                            isError = emailError,
                            errorMessage = "Invalid email address",
                            hint = "Enter your email"
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Password",
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                        )
                        Spacer(Modifier.height(4.dp))
                        CustomOutlinedTextField(
                            value = password,
                            hint = "Enter your password",
                            onValueChange = onPasswordChange,
                            onFocusChange = {},
                            trailingIconResId = iconPasswordVisibility,
                            onTrailingIconClick = { onPasswordVisibilityChange() },
                            isError = passwordError,
                            errorMessage = "Password error",
                            visualTransformation = if (passwordVisibility) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )
                    }
                    Button(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .height(55.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            fontFamily = poppinsFontFamily,
                            text = "Sign up",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 70.dp)
                    ) {
                        Text(
                            text = "Already have an account?",
                            fontFamily = poppinsFontFamily,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = " " + "Sign in",
                            fontFamily = poppinsFontFamily,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable {}
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun SignUpScreenContentPreview(
) {
    ListifyTheme(
        dynamicColor = false
    ) {
        SignUpScreenContent(
            email = "",
            password = "",
            onEmailChange = {},
            onEmailFocusChange = {},
            onPasswordChange = {},
            passwordVisibility = false,
            onPasswordVisibilityChange = {},
            emailError = false,
            iconPasswordVisibility = R.drawable.icon_visibility,
            passwordError = false
        )
    }
}