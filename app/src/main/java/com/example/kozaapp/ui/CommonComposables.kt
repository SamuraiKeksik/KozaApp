package com.example.kozaapp.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kozaapp.R
import com.example.kozaapp.AuthScreenEnum

@Composable
fun StandardOutlineTextField(
    label: String,
    @StringRes placeholder: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isInputWrong: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Spacer(modifier = Modifier.height(15.dp))
    OutlinedTextField(
        label = { Text(text = label, fontSize = 16.sp) },
        placeholder = { Text(text = stringResource(placeholder)) },
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier.fillMaxWidth(),
        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
        isError = isInputWrong,
        keyboardOptions = keyboardOptions,
    )
}
@Composable
fun ErrorText(
    @StringRes text: Int?,
) {
    Text(
        text = stringResource(text ?: R.string.empty_string),
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.error
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KozaAppBar(
    currentScreen: AuthScreenEnum,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
){
    TopAppBar(
        title = { Text(stringResource(currentScreen.title))},
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