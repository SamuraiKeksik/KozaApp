package com.example.kozaapp.auth.ui.screens

import android.util.Log
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
fun RegistrationScreen2() {
    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column (
            verticalArrangement = Arrangement.Top
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
                    text = stringResource(R.string.registration_button_label)
                )
            }
            Text(
                text = stringResource(R.string.login_if_already_registered_label),
                modifier = Modifier.clickable{
                    //TODO("Смена экрана")
                })
        }


    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreen2Preview(){
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RegistrationScreen2(
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreen2DarkThemePreview(){
    AppTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            RegistrationScreen2(
            )
        }
    }
}