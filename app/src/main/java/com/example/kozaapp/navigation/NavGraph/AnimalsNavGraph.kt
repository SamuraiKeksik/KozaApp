package com.example.kozaapp.navigation.NavGraph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.kozaapp.features.animals.goats.ui.GoatDetailsScreen
import com.example.kozaapp.features.animals.goats.ui.GoatEditScreen
import com.example.kozaapp.features.animals.goats.ui.GoatEntryScreen
import com.example.kozaapp.features.animals.ui.screens.AnimalsCardsScreen
import com.example.kozaapp.features.animals.ui.screens.GoatsScreen
import com.example.kozaapp.navigation.AnimalsScreen
import com.example.kozaapp.navigation.GoatsScreen

fun NavGraphBuilder.animalsNavGraph(
    navController: NavHostController,
) {

        navigation(
            startDestination = AnimalsScreen.Animals.route,
            route = NavGraph.ANIMALS_NAV_GRAPH_ROUTE,
        ) {
            composable(AnimalsScreen.Animals.route) {
                AnimalsCardsScreen(
                    navigateToGoatsScreen = { navController.navigate(NavGraph.GOATS_NAV_GRAPH_ROUTE) },
                    navigateToCowsScreen = { navController.navigate(NavGraph.COWS_NAV_GRAPH_ROUTE) },
                    navigateToChickenScreen = {}
                )
            }
            navigation(
                startDestination = GoatsScreen.Goats.route,
                route = NavGraph.GOATS_NAV_GRAPH_ROUTE
            ){
                composable(GoatsScreen.Goats.route) {
                    GoatsScreen(
                        navigateToGoatEntry = { navController.navigate(GoatsScreen.GoatCreation.route) },
                        navigateToGoatDetails = { navController.navigate(GoatsScreen.GoatDetails.passId(it)) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(GoatsScreen.GoatCreation.route) {
                    GoatEntryScreen(
                        navigateBack = { navController.popBackStack() },
                        onNavigateUp = { navController.navigateUp() }
                    )
                }
                composable(
                    route = GoatsScreen.GoatDetails.route,
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    GoatDetailsScreen(
                        navigateBack = { navController.popBackStack() },
                        navigateToEditGoat = { navController.navigate(GoatsScreen.GoatEdit.passId(it)) },
                    )
                }
                composable(
                    route = GoatsScreen.GoatEdit.route,
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    GoatEditScreen(
                        navigateBack = { navController.popBackStack() },
                    )
                }
            }

        }

}