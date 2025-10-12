package com.example.kozaapp.features.animals.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kozaapp.R
import com.example.kozaapp.features.animals.goats.ui.GoatsViewModel
import com.example.kozaapp.features.animals.model.Goat
import com.example.kozaapp.ui.theme.AppTheme

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GoatsScreen(
    navigateToGoatEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoatsViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val goatsUiState by viewModel.goatsUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoatsBody(
            goatsList = goatsUiState.goatsList,
            onGoatClick = navigateToItemUpdate,
            modifier = modifier.fillMaxSize(),
        )
        FloatingActionButton(
            onClick = { navigateToGoatEntry() },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(R.dimen.padding_large))
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }

}

@Composable
private fun GoatsBody(
    goatsList: List<Goat>,
    onGoatClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (goatsList.isEmpty()) {
            Text(
                "EMPTY!", //ToDo: Сделать нормальную заглушку под пустой список
                modifier = Modifier.fillMaxSize()
            )
        } else {
            GoatsList(
                goatsList = goatsList,
                onItemClick = { onGoatClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                    .fillMaxHeight(),

            )
        }
    }
}

@Composable
private fun GoatsList(
    goatsList: List<Goat>,
    onItemClick: (Goat) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(items = goatsList, key = { it.id }) {goat ->
            GoatCard(
                goat = goat,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(goat) })

        }
    }
}

@Composable
private fun GoatCard(
    goat: Goat,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = goat.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = goat.gender.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = goat.description,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    @Composable
    fun GoatListItem(
        goat: Goat,
        modifier: Modifier = Modifier,
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = modifier,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .sizeIn(minHeight = 72.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = goat.name,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = goat.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp))

                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}


@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GoatsBodyPreview() {
    AppTheme {
        GoatsBody(listOf(
            Goat(1, "Biba", "Female", "empty", description = "Very good goat!"),
            Goat(2, "Boba", "Male", "empty", description = "Very nice goat!"),
            Goat(3, "Buba", "Unknown", "empty", description = "Very bad goat!")
        ), onGoatClick = {})
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeBodyEmptyListPreview() {
    AppTheme {
        GoatsBody(listOf(), onGoatClick = {})
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InventoryItemPreview() {
    AppTheme {
        GoatCard(
            Goat(1, "Biba", "Female", "empty", description = "Very good goat!"),
        )
    }
}
