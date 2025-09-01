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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.auth.data.AuthViewModel
import com.example.kozaapp.ui.KozaAppBar
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
    viewModel: AuthViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    startScreen: AuthScreenEnum = AuthScreenEnum.GreetingScreen
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AuthScreenEnum.valueOf(
        backStackEntry?.destination?.route ?: AuthScreenEnum.GreetingScreen.name
    )

    Scaffold(
        topBar = {
            KozaAppBar(
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
            composable(AuthScreenEnum.GreetingScreen.name) {
                _root_ide_package_.com.example.kozaapp.auth.ui.screens.GreetingScreen(
                    onLoginButtonClicked = { navController.navigate(AuthScreenEnum.LoginScreen.name) },
                    onRegistrationButtonClicked = { navController.navigate(AuthScreenEnum.RegistrationScreen1.name) },
                    onContinueWithoutLoginButtonClicked = {
                        navController.navigate(AuthScreenEnum.GreetingScreen.name)
                        //ToDo: Сделать экран основного приложения
                    },
                )
            }
            composable(AuthScreenEnum.LoginScreen.name) {
                viewModel.resetUiStateError()
                _root_ide_package_.com.example.kozaapp.auth.ui.screens.LoginScreen(
                    viewModel = viewModel,
                    onLoginButtonClicked = {
                        navController.navigate(AuthScreenEnum.GreetingScreen.name)
                        //ToDo: Сделать экран основного приложения
                    },
                    onRegistrationButtonClicked = { navController.navigate(AuthScreenEnum.RegistrationScreen1.name) },
                    onForgotPasswordButtonClicked = { navController.navigate(AuthScreenEnum.PasswordRecoveryScreen1.name) }
                )
            }
            composable(AuthScreenEnum.RegistrationScreen1.name) {
                viewModel.resetUiStateError()
                _root_ide_package_.com.example.kozaapp.auth.ui.screens.RegistrationScreen1(
                    viewModel = viewModel,
                    onContinueButtonClicked = { navController.navigate(AuthScreenEnum.RegistrationScreen2.name) },
                    onLoginButtonClicked = { navController.navigate(AuthScreenEnum.LoginScreen.name) },
                )
            }
            composable(AuthScreenEnum.RegistrationScreen2.name) {
                viewModel.resetUiStateError()
                _root_ide_package_.com.example.kozaapp.auth.ui.screens.RegistrationScreen2(
                    viewModel = viewModel,
                    onRegistrationButtonClicked = { navController.navigate(AuthScreenEnum.RegistrationScreen3.name) },
                    onLoginButtonClicked = { navController.navigate(AuthScreenEnum.LoginScreen.name) },
                )
            }
            composable(AuthScreenEnum.RegistrationScreen3.name) {
                _root_ide_package_.com.example.kozaapp.auth.ui.screens.RegistrationScreen3(
                    onContinueButtonClicked = {
                        //ToDo: Сделать основной экран приложения
                        navController.navigate(AuthScreenEnum.GreetingScreen.name)
                    }
                )
            }
            composable(AuthScreenEnum.PasswordRecoveryScreen1.name) {
                viewModel.resetUiStateError()
                _root_ide_package_.com.example.kozaapp.auth.ui.screens.PasswordRecoveryScreen1(
                    viewModel = viewModel,
                    onContinueButtonClicked = { navController.navigate(AuthScreenEnum.PasswordRecoveryScreen2.name) },
                    onLoginButtonClicked = { navController.navigate(AuthScreenEnum.LoginScreen.name) },
                )
            }
            composable(AuthScreenEnum.PasswordRecoveryScreen2.name) {
                viewModel.resetUiStateError()
                _root_ide_package_.com.example.kozaapp.auth.ui.screens.PasswordRecoveryScreen2(
                    viewModel = viewModel,
                    onRecoverPasswordButtonClicked = { navController.navigate(AuthScreenEnum.PasswordRecoveryScreen3.name) },
                    onLoginButtonClicked = { navController.navigate(AuthScreenEnum.LoginScreen.name) },
                )
            }
            composable(AuthScreenEnum.PasswordRecoveryScreen3.name) {
                viewModel.resetUiStateError()
                _root_ide_package_.com.example.kozaapp.auth.ui.screens.PasswordRecoveryScreen3(
                    viewModel = viewModel,
                    onChangePasswordButtonClicked = {
                        //ToDo: Сделать основной экран приложения
                        navController.navigate(AuthScreenEnum.GreetingScreen.name)
                    }
                )
            }
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
