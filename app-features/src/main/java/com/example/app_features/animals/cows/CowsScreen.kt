package com.example.app_features.animals.cows

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
import com.example.app_data.animals.cows.CowBreed
import com.example.app_data.animals.cows.CowEntity
import com.example.app_data.animals.cows.Status
import com.example.app_features.EmptyScreenFiller
import com.example.app_features.R
import com.example.app_features.theme.AppTheme
import java.time.LocalDate
import java.util.UUID


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CowsScreen(
    navigateToCowEntry: () -> Unit,
    navigateToCowDetails: (UUID) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CowsViewModel = hiltViewModel(),
) {
    val cowsUiState by viewModel.cowsUiState.collectAsState()
    if(cowsUiState.isLoading) {
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
            CowsBody(
                cowsList = cowsUiState.cowsList,
                onCowClick = navigateToCowDetails,
                modifier = modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_medium)),
            )
            FloatingActionButton(
                onClick = { navigateToCowEntry() },
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
fun CowsBody(
    cowsList: List<CowEntity>,
    onCowClick: (UUID) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (cowsList.isEmpty()) {
            EmptyScreenFiller(
                header = R.string.empty_animals_list_label1,
                text = R.string.empty_animals_list_label2,
                image = R.drawable.missing_pet
            )
        } else {
            CowsList(
                cowsList = cowsList,
                onCowClick = { onCowClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.fillMaxHeight(),

            )
        }
    }
}

@Composable
fun CowsList(
    cowsList: List<CowEntity>,
    onCowClick: (CowEntity) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))

    ) {
        items(items = cowsList, key = { it.id }) {cow ->
            CowCard(
                cowEntity = cow,
                modifier = Modifier
                    .clickable { onCowClick(cow) })

        }
    }
}

@Composable
fun CowCard(
    cowEntity: CowEntity,
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
                text = cowEntity.name,
                style = MaterialTheme.typography.displaySmall,
                maxLines = 1
            )
            Text(
                text = stringResource(
                    R.string.gender_details_label,
                    stringResource(cowEntity.gender.valueRes)
                ),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(
                    R.string.status_details_label,
                    stringResource(cowEntity.status.valueRes)
                ),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CowsBodyPreview() {
    AppTheme {
        CowsBody(listOf(
            CowEntity(id = UUID.randomUUID(),
                name = "Biba",
                motherId = null,
                fatherId = null,
                gender = Gender.FEMALE,
                breed = CowBreed.OTHER,
                description = "Very good cow!",
                status = Status.OTHER,
                weight = 8,
                birthDate = LocalDate.now().toEpochDay(),
            ),
        ), onCowClick = {})
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CowsEmptyScreenPreview() {
    AppTheme {
        CowsBody(listOf(), onCowClick = {})
    }
}
