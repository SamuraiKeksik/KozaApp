package com.example.kozaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.kozaapp.AuthScreen
import com.example.kozaapp.auth.ui.screens.GreetingScreen
import com.example.kozaapp.auth.ui.screens.RegistrationScreen1
import com.example.kozaapp.mainApp.ui.screens.MainScreen
import com.example.kozaapp.ui.theme.AppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                KozaApp()
            }
        }
    }
}

@Serializable object Auth
@Serializable object Main
@Composable
fun KozaApp(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreensEnum.AuthScreen.name
    ){
        navigation<Auth>(startDestination = AuthScreenEnum.GreetingScreen.name){
            composable(AuthScreenEnum.GreetingScreen.name){
                GreetingScreen(
                    onRegistrationButtonClicked = {},
                    onLoginButtonClicked = {},
                    onContinueWithoutLoginButtonClicked = {})
            }
        }
    }
}

private enum class AppScreensEnum(){
    AuthScreen,
    MainScreen,
}