package com.example.kozaapp.navigation.NavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.features.main.ui.MainNavGraph

@Composable
fun RootNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = NavGraph.ROOT_NAV_GRAPH_ROUTE,
        startDestination = NavGraph.AUTH_NAV_GRAPH_ROUTE
    ) {
        authNavGraph(
            navController = navController,
            onLoginSuccess = {
                navController.navigate(NavGraph.MAIN_NAV_GRAPH_ROUTE) {
                    popUpTo(NavGraph.AUTH_NAV_GRAPH_ROUTE){
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
        )
        composable(route = NavGraph.MAIN_NAV_GRAPH_ROUTE) {
            MainNavGraph()
        }
    }
}