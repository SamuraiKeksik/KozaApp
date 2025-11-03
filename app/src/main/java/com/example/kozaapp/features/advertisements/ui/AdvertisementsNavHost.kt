package com.example.kozaapp.features.advertisements.ui

import AdvertisementDetailsDestination
import AdvertisementDetailsScreen
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.R
import com.example.kozaapp.features.animals.ui.screens.AnimalsCardsDestination
import com.example.kozaapp.features.animals.ui.screens.GoatsDestination
import com.example.kozaapp.ui.NavigationDestination


object AdvertisementsScreenDestination : NavigationDestination{
    override val route = "AdvertisementsScreen"
    @StringRes
    override val titleRes = R.string.empty_string
    override val showBottomBar = true
}

@Composable
fun AdvertisementsNavHost (
    //viewModel: AdvertisementsViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    startDestination: String = AdvertisementsCardsDestination.route,
){
    val advertisementsDestinations = listOf(
        AdvertisementsCardsDestination,
        AdvertisementDetailsDestination,
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = advertisementsDestinations.find { destination ->
        backStackEntry?.destination?.route?.startsWith(destination.route) == true
    } ?: AdvertisementsCardsDestination

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable(AdvertisementsCardsDestination.route) {
                AdvertisementsCardsScreen(
                    navigateToAdvertisementDetails = {
                        navController.navigate(
                            AdvertisementDetailsDestination.route
                        )
                    }
                )
            }
            composable(AdvertisementDetailsDestination.route) {
                AdvertisementDetailsScreen(

                )
            }
        }
    }

