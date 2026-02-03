package com.example.app_features.animals.chickens

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_data.animals.Gender
import com.example.app_data.animals.chickens.ChickenBreed
import com.example.app_data.animals.chickens.ChickenEntity
import com.example.app_data.animals.chickens.Status
import com.example.app_features.EmptyScreenFiller
import com.example.app_features.R
import com.example.app_features.theme.AppTheme
import java.time.LocalDate
import java.util.UUID


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChickensScreen(
    navigateToChickenEntry: () -> Unit,
    navigateToChickenDetails: (UUID) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChickensViewModel = hiltViewModel(),
) {
    val chickensUiState by viewModel.chickensUiState.collectAsState()
    if(chickensUiState.isLoading) {
        Box(
            Modifier.fillMaxSize().padding(0.dp, 50.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.TopCenter
        ){
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
    else{
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ChickensBody(
                chickensList = chickensUiState.chickensList,
                onChickenClick = navigateToChickenDetails,
                modifier = modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_medium)),
            )
            FloatingActionButton(
                onClick = { navigateToChickenEntry() },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(dimensionResource(R.dimen.padding_extra_large))
                    .padding(0.dp,0.dp,0.dp,dimensionResource(R.dimen.padding_medium))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    }


}

@Composable
fun ChickensBody(
    chickensList: List<ChickenEntity>,
    onChickenClick: (UUID) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (chickensList.isEmpty()) {
            EmptyScreenFiller(
                header = R.string.empty_animals_list_label1,
                text = R.string.empty_animals_list_label2,
                image = R.drawable.missing_pet
            )
        } else {
            ChickensList(
                chickensList = chickensList,
                onChickenClick = { onChickenClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.fillMaxHeight(),

            )
        }
    }
}

@Composable
fun ChickensList(
    chickensList: List<ChickenEntity>,
    onChickenClick: (ChickenEntity) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))

    ) {
        items(items = chickensList, key = { it.id }) {chicken ->
            ChickenCard(
                chickenEntity = chicken,
                modifier = Modifier
                    .clickable { onChickenClick(chicken) })

        }
    }
}

@Composable
fun ChickenCard(
    chickenEntity: ChickenEntity,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = chickenEntity.name,
                style = MaterialTheme.typography.displaySmall,
                maxLines = 1
            )
            Text(
                text = stringResource(
                    R.string.gender_details_label,
                    stringResource(chickenEntity.gender.valueRes)
                ),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(
                    R.string.status_details_label,
                    stringResource(chickenEntity.status.valueRes)
                ),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChickensBodyPreview() {
    AppTheme {
        ChickensBody(listOf(
            ChickenEntity(id = UUID.randomUUID(),
                name = "Biba",
                motherId = null,
                fatherId = null,
                gender = Gender.FEMALE,
                breed = ChickenBreed.OTHER,
                description = "Very good chicken!",
                status = Status.OTHER,
                weight = 8,
                birthDate = LocalDate.now().toEpochDay(),
            ),
        ), onChickenClick = {})
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChickensEmptyScreenPreview() {
    AppTheme {
        ChickensBody(listOf(), onChickenClick = {})
    }
}
