package com.example.kozaapp.auth.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kozaapp.R
import com.example.kozaapp.auth.data.AuthViewModel
import com.example.kozaapp.ui.theme.AppTheme

@Composable
fun GreetingScreen(
    viewModel: AuthViewModel,
    onRegistrationButtonClicked: () -> Unit,
    onLoginButtonClicked: () -> Unit,
    onContinueWithoutLoginButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(R.string.greetings_screen_header),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
            )
            Text(
                text = stringResource(R.string.greetings_screen_text_1),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = stringResource(R.string.greetings_screen_text_2),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = stringResource(R.string.greetings_screen_text_3),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = stringResource(R.string.greetings_screen_text_4),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
            )
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
                onClick = {
                    onRegistrationButtonClicked()
                }
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.registration_label)
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                shape = MaterialTheme.shapes.small,
                onClick = {
                    onLoginButtonClicked()
                }
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.login_button_label)
                )
            }
            Text(
                text = stringResource(R.string.continue_without_login_label),
                modifier = Modifier.clickable
                {
                    onContinueWithoutLoginButtonClicked()
                }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingScreenPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            _root_ide_package_.com.example.kozaapp.AuthScreen(
                startScreen = _root_ide_package_.com.example.kozaapp.AuthScreenEnum.GreetingScreen
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingScreenDarkThemePreview() {
    AppTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            _root_ide_package_.com.example.kozaapp.AuthScreen(
                startScreen = _root_ide_package_.com.example.kozaapp.AuthScreenEnum.GreetingScreen
            )
        }
    }
}