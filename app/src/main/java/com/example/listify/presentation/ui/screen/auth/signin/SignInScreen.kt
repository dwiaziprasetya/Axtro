package com.example.listify.presentation.ui.screen.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listify.R
import com.example.listify.presentation.theme.ListifyTheme
import com.example.listify.presentation.theme.poppinsFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignInScreen() {
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

    SignInScreenContent(
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
fun SignInScreenContent(
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
                text = "Sign in to your\naccount",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 32.sp,
                lineHeight = 32.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Enter your email and password to sign in",
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
                    Button(
                        modifier = Modifier
                            .height(55.dp)
                            .fillMaxWidth()
                            .border(
                                color = Color(0xFFE3E3E3),
                                width = 1.dp,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_google),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                fontFamily = poppinsFontFamily,
                                text = "Continue with Google",
                                fontSize = 14.sp,
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Divider(
                            modifier = Modifier.weight(2f),
                            thickness = 1.dp,
                            color = Color(0xFFCAC8C8)
                        )
                        Text(
                            text = "Or sign in with",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontSize = 14.sp
                        )
                        Divider(
                            modifier = Modifier.weight(2f),
                            thickness = 1.dp,
                            color = Color(0xFFCAC8C8)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    CustomOutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        onFocusChange = onEmailFocusChange,
                        trailingIconResId = null,
                        onTrailingIconClick = {},
                        isError = emailError,
                        errorMessage = "Invalid email address",
                        hint = "Email"
                    )
                    Spacer(Modifier.height(16.dp))
                    CustomOutlinedTextField(
                        value = password,
                        hint = "Password",
                        onValueChange = onPasswordChange,
                        onFocusChange = {},
                        trailingIconResId = iconPasswordVisibility,
                        onTrailingIconClick = { onPasswordVisibilityChange() },
                        isError = passwordError,
                        errorMessage = "Password error",
                        visualTransformation = if (passwordVisibility) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Forgot password?",
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.End)
                            .clickable {}
                    )
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
                            text = "Sign in",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 70.dp)
                    ) {
                        Text(
                            text = "Don't have an account?",
                            fontFamily = poppinsFontFamily,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = " " + "Sign up",
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

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    trailingIconResId: Int? = null,
    onTrailingIconClick: () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean,
    errorMessage: String,
    hint: String = ""
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color(0xFFCAC8C8),
        ),
        value = value,
        onValueChange = onValueChange,
        placeholder =  {
            Text(
                text = hint,
                color = MaterialTheme.colorScheme.outline,
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .onFocusChanged {
                isFocused = it.isFocused
                onFocusChange(isFocused)
            },
        trailingIcon = {
            if (trailingIconResId != null) {
                Icon(
                    painter = painterResource(trailingIconResId),
                    contentDescription = null,
                    tint = when {
                        isError -> MaterialTheme.colorScheme.error
                        isFocused -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onPrimary
                    },
                    modifier = Modifier.clickable { onTrailingIconClick() }
                )
            }
        },
        visualTransformation = visualTransformation,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = poppinsFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
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
        SignInScreenContent(
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