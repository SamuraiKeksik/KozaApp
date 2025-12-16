package com.example.app_features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        verticalAlignment = Alignment.CenterVertically,
    ){
        ExpandButton(
            expanded = expanded,
            onClick = onExpandClick,
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = label)
        }

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
        Box(modifier = Modifier.background(Color.White))
        {
            ExpandLabel(
                "Label",
                true,
                {},
                {}
            )
        }
    }
}