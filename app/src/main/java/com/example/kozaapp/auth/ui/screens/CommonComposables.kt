package com.example.kozaapp.auth.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StandardOutlineTextField(
    label: String,
    value: String,
    onValueChange: () -> Unit,
){
    Spacer(modifier = Modifier.height(15.dp))
    OutlinedTextField(
        label = { Text(text = label, fontSize = 16.sp) },
        value = value,
        onValueChange = {onValueChange()},
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle.Default.copy(fontSize = 20.sp)
    )
}
