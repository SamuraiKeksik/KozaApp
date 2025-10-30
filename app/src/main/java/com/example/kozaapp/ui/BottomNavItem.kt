package com.example.kozaapp.features.animals.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kozaapp.R

object BottomNavItems {
    val itemsList = listOf(
        BottomNavItem.HomeScreen,
        BottomNavItem.AdvertisementsScreen,
    )
}

sealed class BottomNavItem(
    val icon: ImageVector,
    @StringRes val labelRes: Int,
    val route: String,
) {
    object HomeScreen : BottomNavItem(
        icon = Icons.Default.Home,
        labelRes = R.string.app_name,
        route = "AnimalsScreen",
    )
    object AdvertisementsScreen : BottomNavItem(
        icon = Icons.Default.ShoppingCart,
        labelRes = R.string.app_name,
        route = "AdvertisementsScreen",
    )
}
