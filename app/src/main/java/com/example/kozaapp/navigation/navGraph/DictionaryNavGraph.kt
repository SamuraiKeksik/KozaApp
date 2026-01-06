package com.example.kozaapp.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.app_features.dictionary.DictionaryAnimalsScreen
import com.example.kozaapp.navigation.DictionaryScreen
import com.example.kozaapp.navigation.Screen

fun NavGraphBuilder.dictionaryNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = DictionaryScreen.Animals.route,
        route = NavGraph.DICTIONARY_NAV_GRAPH_ROUTE
    ){
        composable(DictionaryScreen.Animals.route){
            DictionaryAnimalsScreen(
                navigateToCategoriesScreen = {
                    navController.navigate(DictionaryScreen.Categories.passAnimal(it))
                }
            )
        }
        composable(
            route = DictionaryScreen.Categories.route,
            arguments = listOf(navArgument("animal") {
                type = NavType.StringType
            })){
            DictionaryAnimalsScreen(
                navigateToCategoriesScreen = {
                    navController.navigate(DictionaryScreen.Categories.passAnimal(it))
                }
            )
        }
    }
}