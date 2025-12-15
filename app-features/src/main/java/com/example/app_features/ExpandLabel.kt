package com.example.app_features

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_features.theme.AppTheme

@Composable
fun ExpandLabel(
    label: String,
    expanded: Boolean,
    onExpandClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ){
        ExpandButton(
            expanded = expanded,
            onClick = onExpandClick,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(label, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(1f))
        AddButton(
            onClick = onAddClick
        )
    }
}

@Composable
@Preview
fun ExpandLabelPreview() {
    AppTheme {
        ExpandLabel(
            "Label",
            true,
            {},
            {}
        )
    }
}