package com.example.kozaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.animals.ui.screens.AnimalsScreen
import com.example.kozaapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                KozaNavigation()
            }
        }
    }
}

@Composable
private fun KozaNavigation(){
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

private enum class AppScreensEnum(){
    AuthScreen,
    MainScreen,
}