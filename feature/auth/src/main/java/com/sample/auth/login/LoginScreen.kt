package com.sample.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.core.components.CircularProgressBar
import com.sample.domain.dto.login.response.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginStateFlow: StateFlow<LoginResponse?>,
    loadingStateFlow: StateFlow<Boolean>,
    onLoginSuccess: (user: LoginResponse) -> Unit,
    onLoginClick: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState = loginStateFlow.collectAsState()
    val loadingState = loadingStateFlow.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(loginState.value) {
        loginState.value?.let(onLoginSuccess)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    scrolledContainerColor = Color.Blue
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .padding(innerPadding).verticalScroll(rememberScrollState()),

            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(25.dp)
                    .align(Alignment.Center)

            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Login Button
                Button(
                    onClick = {
                        onLoginClick(username, password)
                        keyboardController?.hide()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Login")
                }
            }
            CircularProgressBar(loadingState.value)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginScreen(
        loginStateFlow = MutableStateFlow<LoginResponse?>(null),
        loadingStateFlow = MutableStateFlow(true),
        onLoginSuccess = {},
        onLoginClick = { _: String, _: String -> }
    )
}