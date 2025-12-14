//package com.example.auth.screens
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.kozaapp.AuthScreen
//import com.example.kozaapp.AuthScreenEnum
//import com.example.kozaapp.R
//import com.example.kozaapp.auth.data.AuthViewModel
//import com.example.kozaapp.ui.ErrorText
//import com.example.kozaapp.ui.StandardOutlineTextField
//import com.example.kozaapp.ui.theme.AppTheme
//
//@Composable
//fun PasswordRecoveryScreen1(
//    viewModel: AuthViewModel,
//    onContinueButtonClicked: () -> Unit,
//    onLoginButtonClicked: () -> Unit,
//) {
//    val uiState by viewModel.uiState.collectAsState()
//    Column(
//        modifier = Modifier.padding(30.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween,
//    ) {
//        Column(
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Text(
//                text = stringResource(R.string.password_recovery_label),
//                style = MaterialTheme.typography.displaySmall,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 30.dp),
//            )
//            Text(
//                text = stringResource(R.string.password_recovery_info_request),
//                style = MaterialTheme.typography.headlineSmall,
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Justify
//            )
//            StandardOutlineTextField(
//                label = stringResource(R.string.email_label),
//                placeholder = R.string.email_example,
//                value = viewModel.email,
//                onValueChange = { viewModel.updateEmail(it) },
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Email,
//                    imeAction = ImeAction.Done,
//                )
//            )
//            Spacer(modifier = Modifier.height(15.dp))
//            ErrorText(text = uiState.error)
//        }
//
//        Column(
//            verticalArrangement = Arrangement.Bottom,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 30.dp),
//        ) {
//            Button(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 5.dp),
//                shape = MaterialTheme.shapes.small,
//                onClick = {
//                    if (viewModel.tryToRecoverPassword()) {
//                        onContinueButtonClicked()
//                    }
//                }
//            ) {
//                Text(
//                    style = MaterialTheme.typography.bodyLarge,
//                    text = stringResource(R.string.continue_button_label)
//                )
//            }
//            Text(
//                text = stringResource(R.string.login_if_remember_password),
//                modifier = Modifier.clickable {
//                    onLoginButtonClicked()
//                })
//        }
//
//
//    }
//}
//
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PasswordRecoveryScreen1Preview() {
//    AppTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            AuthScreen(
//                startScreen = AuthScreenEnum.PasswordRecoveryScreen1
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PasswordRecoveryScreen1DarkThemePreview() {
//    AppTheme(darkTheme = true) {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            AuthScreen(
//                startScreen = AuthScreenEnum.PasswordRecoveryScreen1
//            )
//        }
//    }
//}