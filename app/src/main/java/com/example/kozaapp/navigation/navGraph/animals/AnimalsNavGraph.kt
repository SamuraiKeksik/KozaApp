package com.example.kozaapp.navigation.navGraph.animals

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.app_features.animals.goats.GoatDetailsScreen
import com.example.app_features.animals.goats.GoatEditScreen
import com.example.app_features.animals.goats.GoatEntryScreen
import com.example.app_features.animals.goats.GoatsScreen
import com.example.app_features.animals.AnimalsCardsScreen
import com.example.app_features.animals.goats.GoatAddParentScreen
import com.example.app_features.animals.goats.GoatChildrenScreen
import com.example.kozaapp.navigation.AnimalsScreen
import com.example.kozaapp.navigation.GoatsScreen
import com.example.kozaapp.navigation.navGraph.MainNavGraph
import com.example.kozaapp.navigation.navGraph.NavGraph

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
                navigateToPigsScreen = { navController.navigate(NavGraph.COWS_NAV_GRAPH_ROUTE) },
                navigateToChickenScreen = { navController.navigate(NavGraph.COWS_NAV_GRAPH_ROUTE) }
            )
        }
        goatsNavGraph(navController = navController)
    }
}