package com.example.kozaapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.auth.data.AuthViewModel
import com.example.kozaapp.ui.AuthAppBar
import com.example.kozaapp.ui.theme.AppTheme

enum class AuthScreenEnum(@StringRes val title: Int) {
    //ToDo: Поменять заголовки на соответствующие
    GreetingScreen(title = R.string.app_name),
    LoginScreen(title = R.string.login_label),
    RegistrationScreen1(title = R.string.registration_label),
    RegistrationScreen2(title = R.string.registration_label),
    RegistrationScreen3(title = R.string.registration_label),
    PasswordRecoveryScreen1(title = R.string.password_recovery_label),
    PasswordRecoveryScreen2(title = R.string.password_recovery_label),
    PasswordRecoveryScreen3(title = R.string.password_recovery_label),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    startScreen: AuthScreenEnum = AuthScreenEnum.GreetingScreen,
    onLoginSuccess: () -> Unit = {},
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AuthScreenEnum.valueOf(
        backStackEntry?.destination?.route ?: AuthScreenEnum.GreetingScreen.name
    )


    Scaffold(
        topBar = {
            AuthAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        })
    { innerPading ->
        NavHost(
            navController = navController,
            startDestination = startScreen.name,
            modifier = Modifier
                .padding(innerPading)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

        }
    }
}
    
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthScreenPreview(){
    AppTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AuthScreen(
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthScreenDarkThemePreview(){
    AppTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AuthScreen(
            )
        }
    }
}
