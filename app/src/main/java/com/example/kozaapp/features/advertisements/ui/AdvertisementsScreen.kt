package com.example.kozaapp.features.advertisements.ui

import AdvertisementDetailsDestination
import AdvertisementDetailsScreen
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kozaapp.R
import com.example.kozaapp.features.animals.goats.ui.GoatDetailsDestination
import com.example.kozaapp.features.animals.goats.ui.GoatEditDestination
import com.example.kozaapp.features.animals.goats.ui.GoatEntryDestination
import com.example.kozaapp.features.animals.ui.screens.AnimalCardsDestination
import com.example.kozaapp.features.animals.ui.screens.BottomNavItems
import com.example.kozaapp.features.animals.ui.screens.GoatsDestination
import com.example.kozaapp.ui.MainAppBar
import com.example.kozaapp.ui.NavigationDestination
import com.example.kozaapp.ui.theme.AppTheme


object AdvertisementsScreenDestination : NavigationDestination{
    override val route = "GoatsScreen"
    @StringRes
    override val titleRes = R.string.goats_label
    override val showBottomBar = true
}

@Composable
fun AdvertisementsScreen (
    viewModel: AdvertisementsViewModel = viewModel(),
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
    } ?: AnimalCardsDestination
    Scaffold(
        topBar = {
            MainAppBar(
                currentScreen = currentDestination,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            if (currentDestination.showBottomBar) {
                NavigationBar{
                    BottomNavItems.itemsList.forEach { item ->
                        NavigationBarItem(
                            modifier = Modifier.padding(top = 10.dp),
                            selected = true,
                            onClick = { navController.navigate(item.route) },
                            icon = {
                                Icon(item.icon, contentDescription = stringResource(item.labelRes))
                            },
                            label = { Text(stringResource(item.labelRes)) }
                        )
                    }
                }
            }
        }
    ) { innerPading ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .padding(innerPading)
                .fillMaxSize()
        ) {
            composable(AnimalCardsDestination.route) {
                AdvertisementsCardsScreen(
                    navigateToAdvertisementDetails = {
                        navController.navigate(
                            AdvertisementDetailsDestination.route
                        )
                    }
                )
            }
            composable(GoatsDestination.route) {
                AdvertisementDetailsScreen(

                )
            }
        }
    }
}
