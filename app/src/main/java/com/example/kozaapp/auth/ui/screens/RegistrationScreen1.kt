package com.example.kozaapp.auth.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kozaapp.R
import com.example.kozaapp.auth.data.AuthViewModel
import com.example.kozaapp.ui.ErrorText
import com.example.kozaapp.ui.StandardOutlineTextField
import com.example.kozaapp.ui.theme.AppTheme

@Composable
fun RegistrationScreen1(
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                text = stringResource(R.string.registration_info_request),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Justify
            )
            StandardOutlineTextField(
                label = stringResource(R.string.email_label),
                placeholder = R.string.email_example,
                value = viewModel.email,
                onValueChange = { viewModel.updateEmail(it) },
                isInputWrong = uiState.isEmailWrong,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                )
            )
            StandardOutlineTextField(
                label = stringResource(R.string.name_label),
                placeholder = R.string.name_example,
                value = viewModel.name,
                onValueChange = { viewModel.updateName(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    autoCorrect = true,
                )
            )
            StandardOutlineTextField(
                label = stringResource(R.string.phone_label),
                placeholder = R.string.phone_example,
                value = viewModel.phone,
                onValueChange = { viewModel.updatePhone(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                )
            )
            StandardOutlineTextField(
                label = stringResource(R.string.address_label),
                placeholder = R.string.address_example,
                value = viewModel.address,
                onValueChange = { viewModel.updateAddress(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    autoCorrect = true,
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
                onClick = {
                    if (viewModel.isEmailValidForRegistration()) {
                        //ToDo: Переход на следующий экран
                    }
                }
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.continue_button_label)
                )
            }
            Text(
                text = stringResource(R.string.continue_without_login_label),
                modifier = Modifier.clickable {
                    //TODO("Смена экрана")
                })
        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreen1Preview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RegistrationScreen1(
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreen1DarkThemePreview() {
    AppTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            RegistrationScreen1(
            )
        }
    }
}