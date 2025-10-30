package com.example.kozaapp.features.main.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.R
import com.example.kozaapp.animals.ui.screens.AnimalsNavHost
import com.example.kozaapp.features.advertisements.ui.AdvertisementsNavHost
import com.example.kozaapp.ui.NavigationDestination

interface AppDestination{
    val route: String
    val icon: ImageVector
    val labelRes: Int
}

object AnimalsRoute : AppDestination{
    override val route = "AnimalsScreen"
    override val icon = Icons.Default.Home
    @StringRes
    override val labelRes = R.string.nickname_example
}
object AdvertisementsRoute : AppDestination{
    override val route = "AdvertisementsScreen"
    override val icon = Icons.Default.ShoppingCart
    @StringRes
    override val labelRes = R.string.nickname_example
}
object ProfileRoute : AppDestination{
    override val route = "ProfileScreen"
    override val icon = Icons.Default.Person
    @StringRes
    override val labelRes = R.string.nickname_example
}

val bottomNavItems = listOf(
    AnimalsRoute,
    AdvertisementsRoute,
    ProfileRoute,
)

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val shouldShowBottomBar = bottomNavItems.any { it.route == currentRoute }


    Scaffold(
        topBar = {
            if (backStackEntry.destination)
        },
        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomNavigationBar(
                    appNavController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AnimalsRoute.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(AnimalsRoute.route){
                AnimalsNavHost()
            }
            composable(AdvertisementsRoute.route){
                AdvertisementsNavHost()
            }
            composable(ProfileRoute.route){
                //ProfileScreen()
            }
        }
    }

}

@Composable
fun AppBottomNavigationBar(
    appNavController: NavHostController,
    currentRoute: String?
) {
    val navGraph = appNavController.graph

    NavigationBar {
        bottomNavItems.forEach { destination ->
            NavigationBarItem(
                selected = destination.route == currentRoute,
                onClick = {
                    appNavController.navigate(destination.route) {
                        // 1. Выгружаем все экраны, кроме стартового (сохранение состояния)
                        popUpTo(navGraph.findStartDestination().id) {
                            saveState = true
                        }
                        // 2. Избегаем создания нескольких копий одной вкладки
                        launchSingleTop = true
                        // 3. Восстанавливаем предыдущее состояние (прокрутка)
                        restoreState = true
                    }
                },
                icon = { Icon(destination.icon, contentDescription = stringResource(destination.labelRes)) },
                label = { Text(stringResource(destination.labelRes)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentScreen: NavigationDestination,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.titleRes)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                // Если есть возможность вернуться назад, показываем кнопку
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.empty_string) // Замените на ваш ресурс "Назад"
                    )
                }
            }
        }
    )
}
