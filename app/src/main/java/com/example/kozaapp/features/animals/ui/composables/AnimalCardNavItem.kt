package com.example.kozaapp.features.animals.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.kozaapp.R
import com.example.kozaapp.animals.ui.screens.AnimalsScreenEnum

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
    val route: AnimalsScreenEnum,
){
    object GoatsScreen: AnimalCardNavItem(
        labelRes = R.string.goats_label,
        backgroundRes = R.drawable.goats_background,
        route = AnimalsScreenEnum.GoatListScreen,
    )
    object CowsScreen: AnimalCardNavItem(
        labelRes = R.string.cows_label,
        backgroundRes = R.drawable.cows_background,
        route = AnimalsScreenEnum.AnimalCardsScreen,
    )
    object ChickensScreen: AnimalCardNavItem(
        labelRes = R.string.chickens_label,
        backgroundRes = R.drawable.chickens_background,
        route = AnimalsScreenEnum.AnimalCardsScreen,
    )
}
