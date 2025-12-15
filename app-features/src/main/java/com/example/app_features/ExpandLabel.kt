package com.example.app_features

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpandLabel(
    label: String,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ){
        Text(label)
        Spacer(modifier = Modifier.weight(1f))
        ExpandButton(
            expanded = expanded,
            onClick = onClick,
        )
    }
}