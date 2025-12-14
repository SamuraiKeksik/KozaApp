//package com.example.auth.screens
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.kozaapp.AuthScreen
//import com.example.kozaapp.AuthScreenEnum
//import com.example.kozaapp.R
//import com.example.kozaapp.ui.theme.AppTheme
//
//@Composable
//fun RegistrationScreen3(
//    onContinueButtonClicked: () -> Unit,
//) {
//    Column(
//        modifier = Modifier.padding(30.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween,
//    ) {
//        Column(
//            verticalArrangement = Arrangement.Top
//        ) {
//            Text(
//                text = stringResource(R.string.registration_label),
//                style = MaterialTheme.typography.displaySmall,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 30.dp),
//            )
//            Text(
//                text = stringResource(R.string.after_registration_text),
//                style = MaterialTheme.typography.headlineSmall,
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Justify
//            )
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
//                    onContinueButtonClicked()
//                }) {
//                Text(
//                    style = MaterialTheme.typography.bodyLarge,
//                    text = stringResource(R.string.continue_button_label)
//                )
//            }
//        }
//
//
//    }
//}
//
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun RegistrationScreen3Preview() {
//    AppTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            AuthScreen(
//                startScreen = AuthScreenEnum.RegistrationScreen3
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun RegistrationScreen3DarkThemePreview() {
//    AppTheme(darkTheme = true) {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            AuthScreen(
//                startScreen = AuthScreenEnum.RegistrationScreen3
//            )
//        }
//    }
//}