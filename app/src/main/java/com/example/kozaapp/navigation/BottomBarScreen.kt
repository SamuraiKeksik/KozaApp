package com.example.kozaapp.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Animals : BottomBarScreen(
        route = AnimalsScreen.Animals.route,
        title = "animals",
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreen(
        route = "advertisements",
        title = "advertisements",
        icon = Icons.Default.Person
    )

    object Settings : BottomBarScreen(
        route = "profile",
        title = "profile",
        icon = Icons.Default.Settings
    )
}