package com.example.app_features.animals.goats

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_data.animals.goats.Breed
import com.example.app_data.animals.goats.Gender
import com.example.app_data.animals.goats.GoatEntity
import com.example.app_data.animals.goats.Status
import com.example.app_features.R
import com.example.app_features.theme.AppTheme
import java.util.UUID

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
    navigateToGoatDetails: (UUID) -> Unit,
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
    goatsList: List<GoatEntity>,
    onGoatClick: (UUID) -> Unit,
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
    goatsList: List<GoatEntity>,
    onGoatClick: (GoatEntity) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(items = goatsList, key = { it.id }) {goat ->
            GoatCard(
                goatEntity = goat,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onGoatClick(goat) })

        }
    }
}

@Composable
private fun GoatCard(
    goatEntity: GoatEntity,
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
                text = goatEntity.name,
                style = MaterialTheme.typography.displaySmall,
            )
            Text(
                text = stringResource(
                    R.string.gender_details_label,
                    goatEntity.gender.toString()
                   // stringResource(goat.gender.labelResId)
                ),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(
                    R.string.status_details_label,
                    goatEntity.status.toString()
                    //stringResource(goat.status.labelResId)
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
            GoatEntity(id = UUID.randomUUID(),
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
