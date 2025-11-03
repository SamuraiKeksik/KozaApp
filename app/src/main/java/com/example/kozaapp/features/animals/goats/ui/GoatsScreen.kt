package com.example.kozaapp.features.animals.ui.screens

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kozaapp.R
import com.example.kozaapp.features.animals.goats.ui.GoatsViewModel
import com.example.kozaapp.features.animals.model.Breed
import com.example.kozaapp.features.animals.model.Gender
import com.example.kozaapp.features.animals.model.Goat
import com.example.kozaapp.features.animals.model.Status
import com.example.kozaapp.ui.NavigationDestination
import com.example.kozaapp.ui.theme.AppTheme

//object GoatsDestination : NavigationDestination{
//    override val route = "GoatsScreen"
//    @StringRes
//    override val titleRes = R.string.goats_label
//    override val showBottomBar = false
//}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GoatsScreen(
    navigateToGoatEntry: () -> Unit,
    navigateToGoatDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoatsViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val goatsUiState by viewModel.goatsUiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoatsBody(
            goatsList = goatsUiState.goatsList,
            onGoatClick = navigateToGoatDetails,
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
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.size(200.dp))
                Text(
                    text = stringResource(R.string.empty_animals_list_label1),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.size(3.dp))
                Text(
                    text = stringResource(R.string.empty_animals_list_label2),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.size(10.dp))
                Image(
                    painterResource(R.drawable.missing_pet),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }

        } else {
            GoatsList(
                goatsList = goatsList,
                onGoatClick = { onGoatClick(it.id) },
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
    onGoatClick: (Goat) -> Unit,
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
                    .clickable { onGoatClick(goat) })

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
            modifier =
                Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = goat.name,
                style = MaterialTheme.typography.displaySmall,
            )
            Text(
                text = stringResource(
                    R.string.gender_details_label,
                    stringResource(goat.gender.labelResId)
                ),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(
                    R.string.status_details_label,
                    stringResource(goat.status.labelResId)
                ),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GoatsBodyPreview() {
    AppTheme {
        GoatsBody(listOf(
            Goat(id = 1,
                "Biba",
                gender = Gender.FEMALE,
                breed = Breed.OTHER,
                description = "Very good goat!",
                status = Status.OTHER,
                weight = 8,
                birthDate = ""),
        ), onGoatClick = {})
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GoatsEmptyScreenPreview() {
    AppTheme {
        GoatsBody(listOf(), onGoatClick = {})
    }
}
