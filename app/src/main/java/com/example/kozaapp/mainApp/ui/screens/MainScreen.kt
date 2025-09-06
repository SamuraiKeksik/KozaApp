package com.example.kozaapp.mainApp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kozaapp.R
import com.example.kozaapp.auth.data.AuthViewModel
import com.example.kozaapp.mainApp.data.AnimalsCommonInfo
import com.example.kozaapp.ui.theme.AppTheme

@Composable
fun InfoCard(info: AnimalsCommonInfo){
    Box(
        modifier = Modifier.wrapContentSize()
    ){
        Image(
            painter = painterResource(info.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(text = info.specie)
            Text(text = info.quantity.toString())
        }
    }
}

@Preview
@Composable
fun InfoCardPreview(){
    InfoCard(AnimalsCommonInfo(specie = "Goat", quantity = 2, background = R.drawable.goats_background))
}

@Composable
fun MainScreen(
    viewModel: AuthViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val animalsSpeciesInfoList = listOf(
        AnimalsCommonInfo(specie = "Goat", quantity = 26, R.drawable.goats_background),
        AnimalsCommonInfo(specie = "Cows", quantity = 0, R.drawable.cows_background),
        AnimalsCommonInfo(specie = "Chickens", quantity = 2, R.drawable.chickens_background),
    )
    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
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
            verticalArrangement = Arrangement.Top,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
           items(animalsSpeciesInfoList){
               info -> InfoCard(info)
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