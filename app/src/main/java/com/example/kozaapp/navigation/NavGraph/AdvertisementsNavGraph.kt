package com.example.kozaapp.navigation.NavGraph

import AdvertisementDetailsScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kozaapp.features.advertisements.ui.AdvertisementsCardsScreen
import com.example.kozaapp.navigation.AdvertisementsScreen

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