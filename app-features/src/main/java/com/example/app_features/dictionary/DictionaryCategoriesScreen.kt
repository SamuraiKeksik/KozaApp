package com.example.app_features.dictionary

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Sick
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.app_data.animals.AnimalType
import com.example.app_data.dictionary.ArticleCategory
import com.example.app_features.R
import com.example.app_features.theme.AppTheme

@Composable
fun DictionaryCategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: DictionaryCategoriesViewModel = hiltViewModel(),
    navigateToArticlesScreen: (AnimalType, ArticleCategory) -> Unit,
    navigateToSicknessTypesScreen:(AnimalType) -> Unit,
){
    val uiState = viewModel.categoriesUiState
    Column(
        modifier = modifier.padding(
            start = 30.dp,
            end = 30.dp,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                CategoryCard(
                    labelRes = R.string.sickness_types,
                    image = R.drawable.categories,
                    onButtonClick = {
                        navigateToSicknessTypesScreen(uiState.selectedAnimalType)
                    },
                )
            }
            item {
                CategoryCard(
                    labelRes = R.string.feeding,
                    image = R.drawable.feeding,
                    onButtonClick = {
                        navigateToArticlesScreen(uiState.selectedAnimalType, ArticleCategory.FEEDING)
                    },
                )
            }
            item {
                CategoryCard(
                    labelRes = R.string.breeding,
                    image = R.drawable.breeding,
                    onButtonClick = {
                        navigateToArticlesScreen(uiState.selectedAnimalType, ArticleCategory.BREEDING)
                    },
                )
            }
        }

    }
}

@Composable
fun CategoryCard(
    @StringRes labelRes: Int,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .widthIn(128.dp, 256.dp)
            .height(100.dp),
        border = BorderStroke(1.dp, color = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Box{
            Button(
                onClick = onButtonClick,
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
                )
                Image(
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
fun CategoryCardPreview() {
    CategoryCard(
        labelRes = R.string.goats_label,
        image = R.drawable.animals,
        modifier = Modifier,
        onButtonClick = {},
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CategoryCardsScreenPreview(){
    AppTheme{
        Surface(modifier = Modifier.fillMaxSize()) {
            //AnimalsScreen()
        }
    }
}