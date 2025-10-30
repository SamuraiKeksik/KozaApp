package com.example.kozaapp.features.animals.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kozaapp.R
import com.example.kozaapp.features.advertisements.ui.AdvertisementsCardsDestination
import com.example.kozaapp.features.advertisements.ui.AdvertisementsScreenDestination

object BottomNavItems {
    val itemsList = listOf(
        BottomNavItem.HomeScreen,
        BottomNavItem.AdvertisementsScreen,
        BottomNavItem.HomeScreen,
        BottomNavItem.HomeScreen,
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
        route = AnimalCardsDestination.route,
    )
    object AdvertisementsScreen : BottomNavItem(
        icon = Icons.Default.ShoppingCart,
        labelRes = R.string.app_name,
        route = AdvertisementsScreenDestination.route,
    )
}