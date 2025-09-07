package com.example.kozaapp.mainApp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kozaapp.AuthScreenEnum
import com.example.kozaapp.R
import com.example.kozaapp.ui.theme.AppTheme

enum class MainScreenEnum(@StringRes val title: Int) {
    //ToDo: Поменять заголовки на соответствующие
    MainScreen(title = R.string.app_name),
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    startScreen: AuthScreenEnum = AuthScreenEnum.GreetingScreen,
) {
    Scaffold(
        bottomBar = {
                NavigationBar {
                    BottomNavItems.itemsList.forEach { item ->
                    NavigationBarItem(
                        modifier = Modifier.padding(top = 10.dp),
                        selected = true,
                        onClick = {navController.navigate(item.route.name)},
                        icon = {
                            Icon(item.icon, contentDescription = stringResource(item.labelRes))
                        },
                        label = { Text(stringResource(item.labelRes))}
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(
                top = 50.dp,
                start = 30.dp,
                end = 30.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = stringResource(R.string.login_label),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(AnimalNavItems.itemsList) { item ->
                    AnimalInfoCard(
                        navItem = item,
                        modifier = Modifier.height(250.dp),
                        onButtonClick = {}
                    )
                }
            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenDarkThemePreview() {
    AppTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen()
        }
    }
}