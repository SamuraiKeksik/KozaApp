package com.example.kozaapp.navigation.navGraph.animals

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.app_features.animals.cows.CowAddParentScreen
import com.example.app_features.animals.cows.CowChildrenScreen
import com.example.app_features.animals.cows.CowDetailsScreen
import com.example.app_features.animals.cows.CowEditScreen
import com.example.app_features.animals.cows.CowEntryScreen
import com.example.app_features.animals.cows.CowsScreen
import com.example.kozaapp.navigation.CowsScreen
import com.example.kozaapp.navigation.navGraph.NavGraph

fun NavGraphBuilder.cowsNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = CowsScreen.Cows.route,
        route = NavGraph.COWS_NAV_GRAPH_ROUTE
    ) {
        composable(CowsScreen.Cows.route) {
            CowsScreen(
                navigateToCowEntry = { navController.navigate(CowsScreen.CowCreation.route) },
                navigateToCowDetails = {
                    navController.navigate(
                        CowsScreen.CowDetails.passId(
                            it
                        )
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(CowsScreen.CowCreation.route) {
            CowEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = CowsScreen.CowDetails.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            CowDetailsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditCow = { navController.navigate(CowsScreen.CowEdit.passId(it)) },
                navigateToAddParent = { id, gender ->
                    navController.navigate(
                        CowsScreen.CowParentAdding.passIdWithGender(id, gender)
                    )
                },
                navigateToParentInfo = { navController.navigate(CowsScreen.CowParentInfo.passId(it)) },
                navigateToChildren = { navController.navigate(CowsScreen.CowChildren.passId(it)) },
                canEdit = true,

                )
        }
        composable(
            route = CowsScreen.CowParentInfo.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            CowDetailsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditCow = { },
                navigateToAddParent = { id, gender -> {} },
                navigateToParentInfo = { navController.navigate(CowsScreen.CowParentInfo.passId(it)) },
                navigateToChildren = { navController.navigate(CowsScreen.CowChildren.passId(it)) },
                canEdit = false
            )
        }
        composable(
            route = CowsScreen.CowEdit.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            CowEditScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = CowsScreen.CowParentAdding.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("parent_gender") { type = NavType.StringType }
            )
        ) {
            CowAddParentScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = CowsScreen.CowChildren.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            )
        ) {
            CowChildrenScreen(
                navigateToCowDetails = { navController.navigate(CowsScreen.CowDetails.passId(it)) }
            )
        }
        composable(
            route = CowsScreen.CowChildren.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            )
        ) {
            CowChildrenScreen(
                navigateToCowDetails = { navController.navigate(CowsScreen.CowChildInfo.passId(it)) }
            )
        }
        composable(
            route = CowsScreen.CowChildInfo.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            )
        ) {
            CowDetailsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditCow = { },
                navigateToAddParent = { id, gender -> {} },
                navigateToParentInfo = { navController.navigate(CowsScreen.CowParentInfo.passId(it)) },
                navigateToChildren = { navController.navigate(CowsScreen.CowChildren.passId(it)) },
                canEdit = false
            )
        }
    }
}