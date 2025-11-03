package com.example.kozaapp.features.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.navigation.BottomBarScreen
import com.example.kozaapp.navigation.NavGraph.NavGraph
import com.example.kozaapp.navigation.NavGraph.animalsNavGraph


@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    //val startDestination by viewModel.startDestination.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            NavHost(
                navController = navController,
                route = NavGraph.MAIN_NAV_GRAPH_ROUTE,
                startDestination = NavGraph.ANIMALS_NAV_GRAPH_ROUTE
            ) {
                animalsNavGraph(navController)
                //AdvertisementsNavGraph(navController)
                //ProfileNavGraph(navController)
            }
        }

    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Animals,
        BottomBarScreen.Profile,
        BottomBarScreen.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        colors = NavigationBarItemDefaults.colors(
            unselectedIconColor = LocalContentColor.current.copy(alpha = 0.6f),
            unselectedTextColor = LocalContentColor.current.copy(alpha = 0.6f),
        ),

        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}
