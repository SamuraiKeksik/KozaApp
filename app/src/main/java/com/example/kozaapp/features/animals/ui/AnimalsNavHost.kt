package com.example.kozaapp.animals.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kozaapp.features.animals.goats.ui.GoatDetailsDestination
import com.example.kozaapp.features.animals.goats.ui.GoatDetailsScreen
import com.example.kozaapp.features.animals.goats.ui.GoatEditDestination
import com.example.kozaapp.features.animals.goats.ui.GoatEditScreen
import com.example.kozaapp.features.animals.goats.ui.GoatEntryDestination
import com.example.kozaapp.features.animals.goats.ui.GoatEntryScreen
import com.example.kozaapp.features.animals.ui.AnimalsViewModel
import com.example.kozaapp.features.animals.ui.screens.AnimalsCardsDestination
import com.example.kozaapp.features.animals.ui.screens.AnimalCardsScreen
import com.example.kozaapp.features.animals.ui.screens.GoatsDestination
import com.example.kozaapp.features.animals.ui.screens.GoatsScreen
import com.example.kozaapp.ui.theme.AppTheme

object AnimalsScreenDestination

@Composable
fun AnimalsNavHost(
    viewModel: AnimalsViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val animalsDestinations = listOf(
        AnimalsCardsDestination,
        GoatsDestination,
        GoatEntryDestination,
        GoatDetailsDestination,
        GoatEditDestination,
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = animalsDestinations.find { destination ->
        backStackEntry?.destination?.route?.startsWith(destination.route) == true
    } ?: AnimalsCardsDestination

    NavHost(
        navController = navController,
        startDestination = AnimalsCardsDestination.route,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable(AnimalsCardsDestination.route) {
            AnimalCardsScreen(
                navigateToGoatsScreen = { navController.navigate(GoatsDestination.route) },
                navigateToCowsScreen = {},
                navigateToChickenScreen = {}
            )
        }
        composable(GoatsDestination.route) {
            GoatsScreen(
                navigateToGoatEntry = { navController.navigate(GoatEntryDestination.route) },
                navigateToGoatDetails = { navController.navigate("${GoatDetailsDestination.route}/${it}") },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(GoatEntryDestination.route) {
            GoatEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = GoatDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(GoatDetailsDestination.goatIdArg) {
                type = NavType.IntType
            })
        ) {
            GoatDetailsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditGoat = { navController.navigate("${GoatEditDestination.route}/${it}") },
            )
        }
        composable(
            route = GoatEditDestination.routeWithArgs,
            arguments = listOf(navArgument(GoatEditDestination.goatIdArg) {
                type = NavType.IntType
            })
        ) {
            GoatEditScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AnimalsScreenPreview() {
    AppTheme {
        AnimalsNavHost()
    }
}