package com.example.kozaapp.features.main.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.AuthScreen
import com.example.kozaapp.animals.ui.screens.AnimalsScreen

private enum class AppScreensEnum(){
    AuthScreen,
    MainScreen,
}

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreensEnum.AuthScreen.name
    ){
        composable(AppScreensEnum.AuthScreen.name){
            AuthScreen(
                onLoginSuccess = {
                    navController.navigate(AppScreensEnum.MainScreen.name){
                        popUpTo(AppScreensEnum.AuthScreen.name){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AppScreensEnum.MainScreen.name){
            AnimalsScreen()
        }
    }
}

