package com.example.kozaapp.auth.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kozaapp.R
import com.example.kozaapp.ui.theme.AppTheme

@Composable
fun PasswordRecoveryScreen3() {
    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column (
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(R.string.password_recovery_label),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),


                )
            Text(
                text = stringResource(R.string.password_recovery_info_request_third),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Justify
            )
            StandardOutlineTextField(
                label = stringResource(R.string.password_label),
                value = stringResource(R.string.password_example),
                onValueChange = {}
            )
            StandardOutlineTextField(
                label = stringResource(R.string.password_confirmation_label),
                value = stringResource(R.string.password_confirmation_example),
                onValueChange = {}
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
                onClick = {}
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.change_password_button_label)
                )
            }
        }


    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PasswordRecoveryScreen3Preview(){
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            PasswordRecoveryScreen3(
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PasswordRecoveryScreen3DarkThemePreview(){
    AppTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            PasswordRecoveryScreen3(
            )
        }
    }
}