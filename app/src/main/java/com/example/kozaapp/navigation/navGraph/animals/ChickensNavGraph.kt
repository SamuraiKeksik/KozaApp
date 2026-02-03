package com.example.kozaapp.navigation.navGraph.animals

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.app_features.animals.chickens.ChickenAddParentScreen
import com.example.app_features.animals.chickens.ChickenChildrenScreen
import com.example.app_features.animals.chickens.ChickenDetailsScreen
import com.example.app_features.animals.chickens.ChickenEditScreen
import com.example.app_features.animals.chickens.ChickenEntryScreen
import com.example.app_features.animals.chickens.ChickensScreen
import com.example.kozaapp.navigation.ChickensScreen
import com.example.kozaapp.navigation.navGraph.NavGraph

fun NavGraphBuilder.chickensNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = ChickensScreen.Chickens.route,
        route = NavGraph.CHICKENS_NAV_GRAPH_ROUTE
    ) {
        composable(ChickensScreen.Chickens.route) {
            ChickensScreen(
                navigateToChickenEntry = { navController.navigate(ChickensScreen.ChickenCreation.route) },
                navigateToChickenDetails = {
                    navController.navigate(
                        ChickensScreen.ChickenDetails.passId(
                            it
                        )
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(ChickensScreen.ChickenCreation.route) {
            ChickenEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = ChickensScreen.ChickenDetails.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            ChickenDetailsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditChicken = { navController.navigate(ChickensScreen.ChickenEdit.passId(it)) },
                navigateToAddParent = { id, gender ->
                    navController.navigate(
                        ChickensScreen.ChickenParentAdding.passIdWithGender(id, gender)
                    )
                },
                navigateToParentInfo = { navController.navigate(ChickensScreen.ChickenParentInfo.passId(it)) },
                navigateToChildren = { navController.navigate(ChickensScreen.ChickenChildren.passId(it)) },
                canEdit = true,

                )
        }
        composable(
            route = ChickensScreen.ChickenParentInfo.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            ChickenDetailsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditChicken = { },
                navigateToAddParent = { id, gender -> {} },
                navigateToParentInfo = { navController.navigate(ChickensScreen.ChickenParentInfo.passId(it)) },
                navigateToChildren = { navController.navigate(ChickensScreen.ChickenChildren.passId(it)) },
                canEdit = false
            )
        }
        composable(
            route = ChickensScreen.ChickenEdit.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            ChickenEditScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = ChickensScreen.ChickenParentAdding.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("parent_gender") { type = NavType.StringType }
            )
        ) {
            ChickenAddParentScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = ChickensScreen.ChickenChildren.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            )
        ) {
            ChickenChildrenScreen(
                navigateToChickenDetails = { navController.navigate(ChickensScreen.ChickenDetails.passId(it)) }
            )
        }
        composable(
            route = ChickensScreen.ChickenChildren.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            )
        ) {
            ChickenChildrenScreen(
                navigateToChickenDetails = { navController.navigate(ChickensScreen.ChickenChildInfo.passId(it)) }
            )
        }
        composable(
            route = ChickensScreen.ChickenChildInfo.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            )
        ) {
            ChickenDetailsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditChicken = { },
                navigateToAddParent = { id, gender -> {} },
                navigateToParentInfo = { navController.navigate(ChickensScreen.ChickenParentInfo.passId(it)) },
                navigateToChildren = { navController.navigate(ChickensScreen.ChickenChildren.passId(it)) },
                canEdit = false
            )
        }
    }
}