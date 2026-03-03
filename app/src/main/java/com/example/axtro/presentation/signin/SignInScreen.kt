package com.example.axtro.presentation.signin

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.axtro.R
import com.example.axtro.core.ui.theme.ListifyTheme
import com.example.axtro.core.ui.theme.poppinsFontFamily
import com.example.axtro.core.util.SnackbarController
import com.example.axtro.core.util.SnackbarEvent
import com.example.axtro.core.util.SnackbarType
import com.example.axtro.presentation.component.CustomOutlinedTextField
import com.example.axtro.presentation.navigation.model.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)

    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(
            context.getString(R.string.web_client_id)
        )
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    val state by viewModel.state.collectAsState()

    var isEmailFocused by remember { mutableStateOf(false) }
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
        state = state,
        email = state.email,
        password = state.password,
        onEmailChange = viewModel::onEmailChange,
        onEmailFocusChange = { isFocused ->
            isEmailFocused = isFocused
        },
        onPasswordChange = viewModel::onPasswordChange,
        passwordVisibility = passwordVisibility,
        onPasswordVisibilityChange = { passwordVisibility = !passwordVisibility },
        passwordError = state.passwordError,
        emailError = state.emailError,
        iconPasswordVisibility = icon,
        navigateToSignUp = {
            navController.navigate(Screen.SignUp.route)
        },
        signIn = {
            viewModel.signInWithEmail()
        },
        onClickGoogleButton = {
            scope.launch {
                try {
                    val result = credentialManager.getCredential(
                        context = context,
                        request = request
                    )

                    val credential = result.credential

                    if (credential is CustomCredential &&
                        credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                    ) {
                        val googleCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)

                        val idToken = googleCredential.idToken
                        viewModel.signInWithGoogle(idToken = idToken)
                    }
                } catch (e: GetCredentialException) {
                    when (e) {
                        is NoCredentialException -> {
                            SnackbarController.sendEvent(
                                SnackbarEvent(
                                    message = "No Google account found on this device",
                                    type = SnackbarType.ERROR
                                )
                            )
                        }
                        else -> {
                            SnackbarController.sendEvent(
                                SnackbarEvent(
                                    message = e.message ?: "Unknown error",
                                    type = SnackbarType.ERROR
                                )
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun SignInScreenContent(
    state: SignInUiState,
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
    navigateToSignUp: () -> Unit,
    signIn: () -> Unit,
    onClickGoogleButton: () -> Unit
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
                        onClick = onClickGoogleButton,
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
                        HorizontalDivider(
                            modifier = Modifier.weight(2f),
                            thickness = 1.dp,
                            color = Color(0xFFCAC8C8)
                        )
                        Text(
                            text = "Or sign in with",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontSize = 14.sp
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(2f),
                            thickness = 1.dp,
                            color = Color(0xFFCAC8C8)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
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
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = signIn,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(18.dp)
                            )
                        } else {
                            Text(
                                fontFamily = poppinsFontFamily,
                                text = "Sign in",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
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
                            modifier = Modifier.clickable {
                                navigateToSignUp()
                            }
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
private fun SignInScreenContentPreview(
) {
    ListifyTheme(
        dynamicColor = false
    ) {
        SignInScreenContent(
            state = SignInUiState(),
            email = "",
            password = "",
            onEmailChange = {},
            onEmailFocusChange = {},
            onPasswordChange = {},
            passwordVisibility = false,
            onPasswordVisibilityChange = {},
            emailError = false,
            iconPasswordVisibility = R.drawable.icon_visibility,
            passwordError = false,
            navigateToSignUp = {},
            signIn = {},
            onClickGoogleButton = {}
        )
    }
}