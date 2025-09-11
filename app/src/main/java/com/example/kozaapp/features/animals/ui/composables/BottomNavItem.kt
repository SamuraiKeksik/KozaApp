package com.example.kozaapp.features.animals.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kozaapp.R
import com.example.kozaapp.animals.ui.screens.AnimalsScreenEnum

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
    val route: AnimalsScreenEnum,
) {
    object HomeScreen : BottomNavItem(
        icon = Icons.Default.Home,
        labelRes = R.string.app_name,
        route = AnimalsScreenEnum.AnimalCardsScreen,
    )
}