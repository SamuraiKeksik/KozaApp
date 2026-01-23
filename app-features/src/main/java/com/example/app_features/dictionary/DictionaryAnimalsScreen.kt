package com.example.app_features.dictionary

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_data.animals.AnimalType
import com.example.app_features.R
import com.example.app_features.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryAnimalsScreen(
    modifier: Modifier = Modifier,
    navigateToCategoriesScreen: (AnimalType) -> Unit,
) {
    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        //containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.dictionary),
                        )
                    },
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { padding ->
        Box(modifier.padding(padding)) {
            Column(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 110.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        AnimalCard(
                            labelRes = R.string.all,
                            image = R.drawable.animals,
                            onButtonClick = navigateToCategoriesScreen,
                            animalType = AnimalType.ALL
                        )
                    }
                    item {
                        AnimalCard(
                            labelRes = R.string.goats_label,
                            image = R.drawable.goat,
                            onButtonClick = navigateToCategoriesScreen,
                            animalType = AnimalType.GOAT
                        )
                    }
                    item {
                        AnimalCard(
                            labelRes = R.string.cows_label,
                            image = R.drawable.cow,
                            onButtonClick = navigateToCategoriesScreen,
                            animalType = AnimalType.COW
                        )
                    }
                    item {
                        AnimalCard(
                            labelRes = R.string.chickens_label,
                            image = R.drawable.chicken,
                            onButtonClick = navigateToCategoriesScreen,
                            animalType = AnimalType.CHICKEN
                        )
                    }
                    item {
                        AnimalCard(
                            labelRes = R.string.pigs,
                            image = R.drawable.pig,
                            onButtonClick = navigateToCategoriesScreen,
                            animalType = AnimalType.PIG
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun AnimalCard(
    @StringRes labelRes: Int,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    onButtonClick: (AnimalType) -> Unit,
    animalType: AnimalType,
) {
    OutlinedCard(
        modifier = modifier
            .widthIn(128.dp)
            .height(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Box {
            Button(
                onClick = { onButtonClick(animalType) },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .alpha(0f),
                shape = RoundedCornerShape(0.dp),
            ) { }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = stringResource(labelRes),
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Icon(
                    modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 10.dp),
                    painter = painterResource(image),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun AnimalCardPreview() {
    AnimalCard(
        labelRes = R.string.goats_label,
        image = R.drawable.animals,
        modifier = Modifier,
        onButtonClick = {},
        animalType = AnimalType.GOAT
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AnimalCardsScreenPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            //AnimalsScreen()
        }
    }
}
