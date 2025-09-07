package com.example.kozaapp.mainApp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kozaapp.R

object BottomNavItems {
    val itemsList = listOf(
        BottomNavItem.HomeScreen,
        BottomNavItem.HomeScreen,
        BottomNavItem.HomeScreen,
        BottomNavItem.HomeScreen,
    )
}

sealed class BottomNavItem(
    val icon: ImageVector,
    @StringRes val labelRes: Int,
    val route: MainScreenEnum,
) {
    object HomeScreen : BottomNavItem(
        icon = Icons.Default.Home,
        labelRes = R.string.app_name,
        route = MainScreenEnum.MainScreen,
    )
}