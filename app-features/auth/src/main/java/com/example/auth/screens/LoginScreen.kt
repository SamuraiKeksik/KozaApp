package com.example.auth.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kozaapp.AuthScreen
import com.example.kozaapp.AuthScreenEnum
import com.example.kozaapp.R
import com.example.kozaapp.auth.data.AuthViewModel
import com.example.kozaapp.ui.ErrorText
import com.example.kozaapp.ui.StandardOutlineTextField
import com.example.kozaapp.ui.theme.AppTheme

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginButtonClicked: () -> Unit,
    onRegistrationButtonClicked: () -> Unit,
    onForgotPasswordButtonClicked: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading){
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp)
                .wrapContentSize(Alignment.Center)
        )
    }

    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            onLoginButtonClicked()
        }
    }
    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.login_label),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
            )
            Text(
                text = stringResource(R.string.login_info_request),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Justify
            )
            StandardOutlineTextField(
                label = stringResource(R.string.nickname_label),
                placeholder = R.string.nickname_example,
                value = viewModel.nickname,
                onValueChange = { viewModel.updateNickname(it) },
                isInputWrong = uiState.isNicknameWrong,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
            )
            StandardOutlineTextField(
                label = stringResource(R.string.password_label),
                placeholder = R.string.password_example,
                value = viewModel.password,
                onValueChange = { viewModel.updatePassword(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.forgot_password_label),
                modifier = Modifier.clickable {
                    onForgotPasswordButtonClicked()
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            ErrorText(text = uiState.error)
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                shape = MaterialTheme.shapes.small,
                enabled = !uiState.isLoading,
                onClick = {
                    viewModel.tryToLogin()
                    if (uiState.isLoginSuccess) {
                        onLoginButtonClicked()
                    }
                }
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.login_button_label)
                )
            }
            Text(
                text = stringResource(R.string.register_if_havent_yet_label),
                modifier = Modifier.clickable {
                    if (!uiState.isLoading)
                        onRegistrationButtonClicked()
                }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AuthScreen(
                startScreen = AuthScreenEnum.LoginScreen
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenDarkThemePreview() {
    AppTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AuthScreen(
                startScreen = AuthScreenEnum.LoginScreen
            )
        }
    }
}