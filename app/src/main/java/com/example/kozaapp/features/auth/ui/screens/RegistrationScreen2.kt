package com.example.kozaapp.features.auth.ui.screens

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
fun RegistrationScreen2(
    viewModel: AuthViewModel,
    onRegistrationButtonClicked: () -> Unit,
    onLoginButtonClicked: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading){
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp)
                .wrapContentSize(Alignment.Center)
        )
    }

    LaunchedEffect(uiState.isRegistrationSuccess) {
        if (uiState.isRegistrationSuccess) {
            onRegistrationButtonClicked()
        }
    }

    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.registration_label),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
            )
            Text(
                text = stringResource(R.string.registration_info_request_second),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Justify
            )
            StandardOutlineTextField(
                label = stringResource(R.string.password_label),
                placeholder = R.string.password_example,
                value = viewModel.password,
                onValueChange = { viewModel.updatePassword(it) },
                isInputWrong = uiState.isPasswordWrong,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                )
            )
            StandardOutlineTextField(
                label = stringResource(R.string.password_confirmation_label),
                placeholder = R.string.password_confirmation_example,
                value = viewModel.passwordConfirmation,
                onValueChange = { viewModel.updatePasswordConfirmation(it) },
                isInputWrong = uiState.isPasswordConfirmationWrong,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                )
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
                    viewModel.tryToRegister()
                }
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.registration_button_label)
                )
            }
            Text(
                text = stringResource(R.string.login_if_already_registered_label),
                modifier = Modifier.clickable {
                    if(!uiState.isLoading)
                        onLoginButtonClicked()
                })
        }


    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreen2Preview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AuthScreen(
                startScreen = AuthScreenEnum.RegistrationScreen2
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreen2DarkThemePreview() {
    AppTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AuthScreen(
                startScreen = AuthScreenEnum.RegistrationScreen2
            )
        }
    }
}