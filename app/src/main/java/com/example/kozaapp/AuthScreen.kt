package com.example.kozaapp.auth.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kozaapp.R


@Composable
fun StandardOutlineTextField(
    label: String,
    @StringRes placeholder: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isInputWrong: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
){
    Spacer(modifier = Modifier.height(15.dp))
    OutlinedTextField(
        label = { Text(text = label, fontSize = 16.sp) },
        placeholder = {Text(text = stringResource(placeholder))},
        value = value,
        onValueChange = {onValueChange(it)},
        modifier = modifier.fillMaxWidth(),
        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
        isError = isInputWrong,
        keyboardOptions = keyboardOptions,
    )
}

@Composable
fun ErrorText(
    @StringRes text: Int?,
){
    Text(
        text = stringResource(text ?: R.string.empty_string),
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.error
        )
    )
}

enum class AuthScreenEnum(){
    GreetingScreen,
    LoginScreen,
    RegistrationScreen1,
    RegistrationScreen2,
    RegistrationScreen3,
    PasswordRecoveryScreen1,
    PasswordRecoveryScreen2,
    PasswordRecoveryScreen3,
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KozaAppTopBar(
    currentScreen: AuthScreenEnum,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    viewModel: AuthViewModel = viewModel(),
    modifier: Modifier = Modifier,
){
    TopAppBar(
        title = {Text(currentScreen.name)},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KozaApp(

){

    val uiState by viewModel.uiState.collectAsState()
}
    
*/
