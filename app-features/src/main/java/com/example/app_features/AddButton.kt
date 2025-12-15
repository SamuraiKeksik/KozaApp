package com.example.app_features

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ){
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}