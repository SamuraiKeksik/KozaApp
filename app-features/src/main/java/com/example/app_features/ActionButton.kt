package com.example.app_features

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ActionButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ){
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}