package com.example.app_features.dictionary

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DonutSmall
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_data.dictionary.DictionaryCategory
import com.example.app_features.R

@Composable
fun DictionaryCategoriesScreen(
    modifier: Modifier = Modifier,
    navigateToGoatsDictionaryScreen: () -> Unit,
    navigateToCowsDictionaryScreen: () -> Unit,
    navigateToChickenDictionaryScreen: () -> Unit,
){
    Column(
        modifier = modifier.padding(
        start = 30.dp,
        end = 30.dp,
    ),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = stringResource(R.string.main_screen_label),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
        )
        Column {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 120.dp)
            ) {
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    CategoryCard(
                        DictionaryCategory.FEEDING,
                        R.string.feeding,
                        Icons.Filled.DonutSmall,
                        onButtonClick = { navigateToGoatsDictionaryScreen() }
                    )
                }
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    CategoryCard(
                        DictionaryCategory.BREEDING,
                        R.string.breeding,
                        Icons.Filled.Pets,
                        onButtonClick = { navigateToGoatsDictionaryScreen() }
                    )
                }
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    CategoryCard(
                        DictionaryCategory.BREEDING,
                        R.string.breeding,
                        Icons.Filled.Pets,
                        onButtonClick = { navigateToGoatsDictionaryScreen() }
                    )
                }
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    CategoryCard(
                        DictionaryCategory.BREEDING,
                        R.string.breeding,
                        Icons.Filled.Pets,
                        onButtonClick = { navigateToGoatsDictionaryScreen() }
                    )
                }
            }

        }

    }
}

@Composable
fun CategoryCard(
    category: DictionaryCategory,
    @StringRes labelRes: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onButtonClick: (DictionaryCategory) -> Unit
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(200.dp),
        border = BorderStroke(1.dp, color = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                onClick = { onButtonClick(category) },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .alpha(0f),
                shape = RoundedCornerShape(0.dp),
            ) { }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(labelRes),
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun DictionaryCategoriesPreview() {
    DictionaryCategoriesScreen(
        navigateToChickenDictionaryScreen = {},
        navigateToCowsDictionaryScreen = {},
        navigateToGoatsDictionaryScreen = {}
    )
}
