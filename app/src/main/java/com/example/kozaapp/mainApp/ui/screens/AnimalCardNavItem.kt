package com.example.kozaapp.mainApp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.kozaapp.R

object AnimalNavItems {
    val itemsList = listOf(
        AnimalCardNavItem.GoatsScreen,
        AnimalCardNavItem.CowsScreen,
        AnimalCardNavItem.ChickensScreen,
    )
}
sealed class AnimalCardNavItem (
    @StringRes val labelRes: Int,
    @DrawableRes val backgroundRes: Int,
    val route: MainScreenEnum,
){
    object GoatsScreen: AnimalCardNavItem(
        labelRes = R.string.goats_label,
        backgroundRes = R.drawable.goats_background,
        route = MainScreenEnum.GoatListScreen,
    )
    object CowsScreen: AnimalCardNavItem(
        labelRes = R.string.cows_label,
        backgroundRes = R.drawable.cows_background,
        route = MainScreenEnum.AnimalCardsScreen,
    )
    object ChickensScreen: AnimalCardNavItem(
        labelRes = R.string.chickens_label,
        backgroundRes = R.drawable.chickens_background,
        route = MainScreenEnum.AnimalCardsScreen,
    )
}
