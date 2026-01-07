package com.example.kozaapp.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.app_features.dictionary.DictionaryAnimalsScreen
import com.example.app_features.dictionary.DictionaryArticlesListScreen
import com.example.app_features.dictionary.DictionaryCategoriesScreen
import com.example.app_features.dictionary.DictionarySicknessesScreen
import com.example.kozaapp.navigation.DictionaryScreen

fun NavGraphBuilder.dictionaryNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = DictionaryScreen.Animals.route,
        route = NavGraph.DICTIONARY_NAV_GRAPH_ROUTE
    ) {
        composable(DictionaryScreen.Animals.route) {
            DictionaryAnimalsScreen(
                navigateToCategoriesScreen = {
                    navController.navigate(DictionaryScreen.Categories.passAnimal(it))
                }
            )
        }
        composable(
            route = DictionaryScreen.Categories.route,
            arguments = listOf(navArgument("animal_type") {
                type = NavType.StringType
            })
        ) {
            DictionaryCategoriesScreen(
                navigateToArticlesScreen = { animal, category ->
                    navController.navigate(
                        DictionaryScreen.ArticlesList.passAnimalWithCategory(
                            animal,
                            category
                        )
                    )
                },
                navigateToSicknessTypesScreen = {
                    navController.navigate(DictionaryScreen.Sicknesses.passAnimalType(it))
                }
            )
        }
        composable(
            route = DictionaryScreen.Sicknesses.route,
            arguments = listOf(navArgument("animal_type") {
                type = NavType.StringType
            })
        ) {
            DictionarySicknessesScreen()
        }
        composable(
            route = DictionaryScreen.ArticlesList.route,
            arguments = listOf(
                navArgument("animal_type") {type = NavType.StringType},
                navArgument("article_category") {type = NavType.StringType},
            )
        ) {
            DictionaryArticlesListScreen ( ){}
        }
        composable(
            route = DictionaryScreen.Article.route,
            arguments = listOf(
                navArgument("article_id") {type = NavType.IntType},
            )
        ) {
            DictionaryArticlesListScreen ( ){}
        }
    }
}