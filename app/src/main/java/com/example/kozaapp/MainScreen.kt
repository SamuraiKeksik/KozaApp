package com.example.kozaapp.mainApp.ui.screens

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.R
import com.example.kozaapp.mainApp.model.GoatsRepository
import com.example.kozaapp.ui.MainAppBar
import com.example.kozaapp.ui.theme.AppTheme

enum class MainScreenEnum(
    @StringRes val title: Int,
    val showBottomBar: Boolean = false,
    ) {
    //ToDo: Поменять заголовки на соответствующие
    AnimalCardsScreen(title = R.string.empty_string, showBottomBar = true,),
    GoatListScreen(title = R.string.goats_label, showBottomBar = false,),
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    startScreen: MainScreenEnum = MainScreenEnum.AnimalCardsScreen,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MainScreenEnum.valueOf(
        backStackEntry?.destination?.route ?: MainScreenEnum.AnimalCardsScreen.name
    )
    Scaffold(
        topBar = {
            MainAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            if (currentScreen.showBottomBar) {
                NavigationBar{
                    BottomNavItems.itemsList.forEach { item ->
                        NavigationBarItem(
                            modifier = Modifier.padding(top = 10.dp),
                            selected = true,
                            onClick = { navController.navigate(item.route.name) },
                            icon = {
                                Icon(item.icon, contentDescription = stringResource(item.labelRes))
                            },
                            label = { Text(stringResource(item.labelRes)) }
                        )
                    }
                }
            }
        }
    ) { innerPading ->
        NavHost(
            navController = navController,
            startDestination = startScreen.name,
            modifier = Modifier
                .padding(innerPading)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            composable(MainScreenEnum.AnimalCardsScreen.name) {
                AnimalCardsScreen()
            }
            composable(MainScreenEnum.GoatListScreen.name) {
                GoatListScreen(
                    GoatsRepository.goats,
                    modifier = Modifier.padding(
                        top = innerPading.calculateTopPadding(),
                        bottom = innerPading.calculateBottomPadding(),
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}