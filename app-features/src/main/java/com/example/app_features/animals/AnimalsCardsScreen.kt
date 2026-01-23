package com.example.app_features.animals

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_features.R
import com.example.app_features.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalsCardsScreen(
    modifier: Modifier = Modifier,
    navigateToGoatsScreen: () -> Unit,
    navigateToCowsScreen: () -> Unit,
    navigateToChickenScreen: () -> Unit,
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
                            text = stringResource(R.string.main_screen_label),
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
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        AnimalInfoCard(
                            labelRes = R.string.goats_label,
                            backgroundRes = R.drawable.goats_background2,
                            onButtonClick = { navigateToGoatsScreen() }
                        )
                    }
                    item {
                        AnimalInfoCard(
                            labelRes = R.string.cows_label,
                            backgroundRes = R.drawable.cows_background2,
                            onButtonClick = { navigateToCowsScreen() }
                        )

                    }
                    item {
                        AnimalInfoCard(
                            labelRes = R.string.chickens_label,
                            backgroundRes = R.drawable.chickens_background2,
                            onButtonClick = { navigateToChickenScreen() }
                        )
                    }
                }

            }
        }

    }
}

@Composable
fun AnimalInfoCard(
    @StringRes labelRes: Int,
    @DrawableRes backgroundRes: Int,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            //Invisible button
            Button(
                onClick = { onButtonClick() },
                modifier = Modifier
                    .matchParentSize()
                    .alpha(0f),
            ) { }
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(backgroundRes),
                    contentDescription = null,
                    modifier = modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
                Column(
                ){
                    Text(
                        text = stringResource(labelRes),
                        modifier = Modifier.padding(10.dp, 10.dp,10.dp,0.dp,),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(R.string.quantity_label, 0),
                        modifier = Modifier.padding(10.dp, 0.dp,10.dp,10.dp,),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }
        }
    }
}


@Preview
@Composable
fun AnimalInfoCardPreview() {
    AnimalInfoCard(
        labelRes = R.string.goats_label,
        backgroundRes = R.drawable.goats_background2,
        modifier = Modifier,
        onButtonClick = {}
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