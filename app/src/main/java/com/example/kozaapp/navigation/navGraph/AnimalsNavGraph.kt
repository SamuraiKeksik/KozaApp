package com.example.kozaapp.navigation.navGraph

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
        ) {
            composable(GoatsScreen.Goats.route) {
                GoatsScreen(
                    navigateToGoatEntry = { navController.navigate(GoatsScreen.GoatCreation.route) },
                    navigateToGoatDetails = {
                        navController.navigate(
                            GoatsScreen.GoatDetails.passId(
                                it
                            )
                        )
                    },
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
                    type = NavType.StringType
                })
            ) {
                GoatDetailsScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditGoat = { navController.navigate(GoatsScreen.GoatEdit.passId(it)) },
                    navigateToAddParent = { id, gender ->
                        navController.navigate(GoatsScreen.GoatParentAdding.passIdWithGender(id, gender)
                    )},
                    navigateToParentInfo = { navController.navigate(GoatsScreen.GoatParentInfo.passId(it)) },
                    navigateToChildren = { navController.navigate(GoatsScreen.GoatChildren.passId(it))  },
                    canEdit = true,

                )
            }
            composable(
                route = GoatsScreen.GoatParentInfo.route,
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                })
            ) {
                GoatDetailsScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditGoat = { },
                    navigateToAddParent = { id, gender -> {} },
                    navigateToParentInfo = { navController.navigate(GoatsScreen.GoatParentInfo.passId(it)) },
                    navigateToChildren = { navController.navigate(GoatsScreen.GoatChildren.passId(it)) },
                    canEdit = false
                )
            }
            composable(
                route = GoatsScreen.GoatEdit.route,
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                })
            ) {
                GoatEditScreen(
                    navigateBack = { navController.popBackStack() },
                )
            }
            composable(
                route = GoatsScreen.GoatParentAdding.route,
                arguments = listOf(
                    navArgument("id") {type = NavType.StringType},
                    navArgument("parent_gender") {type = NavType.StringType}
                    )
            ) {
                GoatAddParentScreen(
                    navigateBack = { navController.popBackStack() },
                )
            }
            composable(
                route = GoatsScreen.GoatChildren.route,
                arguments = listOf(
                    navArgument("id") {type = NavType.StringType},
                    )
            ) {
                GoatChildrenScreen(
                    navigateToGoatDetails = {navController.navigate(GoatsScreen.GoatDetails.passId(it))}
                )
            }
            composable(
                route = GoatsScreen.GoatChildren.route,
                arguments = listOf(
                    navArgument("id") {type = NavType.StringType},
                    )
            ) {
                GoatChildrenScreen(
                    navigateToGoatDetails = {navController.navigate(GoatsScreen.GoatChildInfo.passId(it))}
                )
            }
            composable(
                route = GoatsScreen.GoatChildInfo.route,
                arguments = listOf(
                    navArgument("id") {type = NavType.StringType},
                    )
            ) {
                GoatDetailsScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditGoat = { },
                    navigateToAddParent = { id, gender -> {} },
                    navigateToParentInfo = { navController.navigate(GoatsScreen.GoatParentInfo.passId(it)) },
                    navigateToChildren = { navController.navigate(GoatsScreen.GoatChildren.passId(it)) },
                    canEdit = false
                )
            }
        }

    }

}