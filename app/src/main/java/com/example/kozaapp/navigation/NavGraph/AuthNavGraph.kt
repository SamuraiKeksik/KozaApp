package com.example.kozaapp.navigation.NavGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kozaapp.auth.data.AuthViewModel
import com.example.kozaapp.features.auth.ui.screens.GreetingScreen
import com.example.kozaapp.features.auth.ui.screens.LoginScreen
import com.example.kozaapp.features.auth.ui.screens.PasswordRecoveryScreen1
import com.example.kozaapp.features.auth.ui.screens.PasswordRecoveryScreen2
import com.example.kozaapp.features.auth.ui.screens.PasswordRecoveryScreen3
import com.example.kozaapp.features.auth.ui.screens.RegistrationScreen1
import com.example.kozaapp.features.auth.ui.screens.RegistrationScreen2
import com.example.kozaapp.features.auth.ui.screens.RegistrationScreen3
import com.example.kozaapp.navigation.AnimalsScreen
import com.example.kozaapp.navigation.AuthScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
){
    navigation(
        startDestination = AuthScreen.Greetings.route,
        route = NavGraph.AUTH_NAV_GRAPH_ROUTE,
    ){
        composable(AuthScreen.Greetings.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            GreetingScreen(
                viewModel = viewModel,
                onLoginButtonClicked = { navController.navigate(AuthScreen.Login.route) },
                onRegistrationButtonClicked = { navController.navigate(AuthScreen.Registration1.route) },
                onContinueWithoutLoginButtonClicked = { onLoginSuccess() },
            )
        }
        composable(AuthScreen.Login.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            viewModel.resetUiStateError()
            LoginScreen(
                viewModel = viewModel,
                onLoginButtonClicked = onLoginSuccess,
                onRegistrationButtonClicked = { navController.navigate(AuthScreen.Registration1.route) },
                onForgotPasswordButtonClicked = { navController.navigate(AuthScreen.PasswordRecovery1.route) }
            )
        }
        composable(AuthScreen.Registration1.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            viewModel.resetUiStateError()
            RegistrationScreen1(
                viewModel = viewModel,
                onContinueButtonClicked = { navController.navigate(AuthScreen.Registration2.route) },
                onLoginButtonClicked = { navController.navigate(AuthScreen.Login.route) },
            )
        }
        composable(AuthScreen.Registration2.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            viewModel.resetUiStateError()
            RegistrationScreen2(
                viewModel = viewModel,
                onRegistrationButtonClicked = { navController.navigate(AuthScreen.Registration3.route) },
                onLoginButtonClicked = { navController.navigate(AuthScreen.Login.route) },
            )
        }
        composable(AuthScreen.Registration3.route) {
            RegistrationScreen3(
                onContinueButtonClicked = onLoginSuccess,
            )
        }
        composable(AuthScreen.PasswordRecovery1.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            viewModel.resetUiStateError()
            PasswordRecoveryScreen1(
                viewModel = viewModel,
                onContinueButtonClicked = { navController.navigate(AuthScreen.PasswordRecovery2.route) },
                onLoginButtonClicked = { navController.navigate(AuthScreen.Login.route) },
            )
        }
        composable(AuthScreen.PasswordRecovery2.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            viewModel.resetUiStateError()
            PasswordRecoveryScreen2(
                viewModel = viewModel,
                onRecoverPasswordButtonClicked = { navController.navigate(AuthScreen.PasswordRecovery3.route) },
                onLoginButtonClicked = { navController.navigate(AuthScreen.Login.route) },
            )
        }
        composable(AuthScreen.PasswordRecovery3.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            viewModel.resetUiStateError()
            PasswordRecoveryScreen3(
                viewModel = viewModel,
                onChangePasswordButtonClicked = {
                    navController.navigate(AnimalsScreen.Animals.route){
                        popUpTo(route = AnimalsScreen.Animals.route) {inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}