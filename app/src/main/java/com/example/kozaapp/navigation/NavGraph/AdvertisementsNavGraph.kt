package com.example.kozaapp.navigation.NavGraph

import AdvertisementDetailsScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.kozaapp.features.advertisements.ui.AdvertisementsCardsScreen
import com.example.kozaapp.features.animals.goats.ui.GoatDetailsScreen
import com.example.kozaapp.features.animals.goats.ui.GoatEditScreen
import com.example.kozaapp.features.animals.goats.ui.GoatEntryScreen
import com.example.kozaapp.features.animals.ui.screens.AnimalsCardsScreen
import com.example.kozaapp.features.animals.ui.screens.GoatsScreen
import com.example.kozaapp.navigation.AdvertisementsScreen
import com.example.kozaapp.navigation.AnimalsScreen
import com.example.kozaapp.navigation.GoatsScreen

fun NavGraphBuilder.advertisementsNavGraph(
    navController: NavHostController,
) {

    navigation(
        startDestination = AdvertisementsScreen.Advertisements.route,
        route = NavGraph.ADVERTISEMENTS_NAV_GRAPH_ROUTE,
    ) {
        composable(AdvertisementsScreen.Advertisements.route) {
            AdvertisementsCardsScreen(
                navigateToAdvertisementDetails = {
                    navController.navigate(AdvertisementsScreen.AdvertisementDetails.passId(it))
                },
            )
        }
        composable(AdvertisementsScreen.AdvertisementDetails.route) {
            AdvertisementDetailsScreen()
        }


    }

}