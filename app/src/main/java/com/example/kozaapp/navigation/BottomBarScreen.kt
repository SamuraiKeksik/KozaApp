package com.example.kozaapp.navigation


import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kozaapp.R

sealed class BottomBarScreen(
    val route: String,
    @StringRes
    val title: Int,
    val icon: ImageVector
) {
    object Animals : BottomBarScreen(
        route = AnimalsScreen.Animals.route,
        title = R.string.animals,
        icon = Icons.Default.Apps
    )

    object Profile : BottomBarScreen(
        route = AdvertisementsScreen.Advertisements.route,
        title = R.string.advertisements,
        icon = Icons.Default.ListAlt
    )

    object Settings : BottomBarScreen(
        route = ProfileScreen.Profile.route,
        title = R.string.profile,
        icon = Icons.Default.Person
    )
}