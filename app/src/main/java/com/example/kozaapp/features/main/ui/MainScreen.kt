package com.example.kozaapp.features.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.AuthScreen
import com.example.kozaapp.animals.ui.screens.AnimalsScreen

enum class MainScreensEnum(){
    AuthScreen,
    MainScreen,
}

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()){
    val navController = rememberNavController()
    val startDestination by viewModel.startDestination.collectAsState()

    if(startDestination != null){
        NavHost(
            navController = navController,
            startDestination = startDestination!!
        ){
            composable(MainScreensEnum.AuthScreen.name){
                AuthScreen(
                    onLoginSuccess = {
                        navController.navigate(MainScreensEnum.MainScreen.name){
                            popUpTo(MainScreensEnum.AuthScreen.name){
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(MainScreensEnum.MainScreen.name){
                AnimalsScreen()
            }
        }
    }

}

