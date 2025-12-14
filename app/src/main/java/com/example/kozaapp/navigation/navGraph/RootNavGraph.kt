package com.example.kozaapp.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = NavGraph.ROOT_NAV_GRAPH_ROUTE,
        startDestination = NavGraph.MAIN_NAV_GRAPH_ROUTE
    ) {
        composable(route = NavGraph.AUTH_NAV_GRAPH_ROUTE){
//            AuthNavGraph(
//                onLoginSuccess = {
//                    navController.navigate(NavGraph.MAIN_NAV_GRAPH_ROUTE) {
//                        popUpTo(NavGraph.AUTH_NAV_GRAPH_ROUTE){
//                            inclusive = true
//                        }
//                        launchSingleTop = true
//                    }
//                },
//            )
        }

        composable(route = NavGraph.MAIN_NAV_GRAPH_ROUTE) {
            MainNavGraph()
        }
    }
}