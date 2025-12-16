package com.example.app_features.animals.goats

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_data.animals.Sickness
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.goats.GoatEntity
import com.example.app_features.ExpandLabel
import com.example.app_features.R
import com.example.app_features.theme.AppTheme
import kotlinx.coroutines.launch
import java.util.UUID

//
//object GoatDetailsDestination : NavigationDestination{
//    override val route = "GoatDetailsScreen"
//    @StringRes
//    override val titleRes = R.string.goat_details_screen_label
//    override val showBottomBar = false
//    const val goatIdArg = "goatId"
//    val routeWithArgs = "$route/{$goatIdArg}"
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoatDetailsScreen(
    navigateToEditGoat: (UUID) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoatDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column( modifier = modifier,
    ) {
        GoatDetailsBody(
            goatDetailsUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteGoat()
                    navigateBack()
                }
            },
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        )
//        FloatingActionButton(
//            onClick = { navigateToEditGoat(uiState.value.goatDetails.id) },
//            shape = MaterialTheme.shapes.medium,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(dimensionResource(R.dimen.padding_large))
//        ) {
//            Icon(
//                imageVector = Icons.Default.Edit,
//                contentDescription = null
//            )
//        }
    }
}

@Composable
private fun GoatDetailsBody(
    goatDetailsUiState: GoatDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        GoatDetails(
            goatEntity = goatDetailsUiState.goatDetails.toGoat())
        GoatVaccinations(vaccinationsList = goatDetailsUiState.goatVaccinations)
        GoatSicknesses(sicknessesList = goatDetailsUiState.goatSicknesses)
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.goat_delete_button_label))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                goatName = goatDetailsUiState.goatDetails.name,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}




@Composable
fun GoatDetails(
    goatEntity: GoatEntity,
    modifier: Modifier = Modifier
) {
    var expanded by remember {mutableStateOf(true)}
    Card(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(10.dp)
            )
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        ExpandLabel(
            label = stringResource(R.string.goat_details_screen_label),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            onActionClick = {},
            imageVector = Icons.Filled.Mode,
        )
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ) {
                GoatDetailsRow(
                    labelResID = R.string.goat_name_label,
                    goatDetail = goatEntity.name,
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen
                                .padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_gender_label,
                    goatDetail = goatEntity.gender.toString(),
                    //goatDetail = stringResource(goat.gender.labelResId),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen
                                .padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.breed_label,
                    goatDetail = goatEntity.breed.toString(),
                    //goatDetail = stringResource(goat.breed.labelResId),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen
                                .padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.status_label,
                    goatDetail = goatEntity.status.toString(),
                    //goatDetail = stringResource(goat.status.labelResId),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_weight_label,
                    goatDetail = goatEntity.weight.toString(),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_birth_date_label,
                    goatDetail = goatEntity.birthDate ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_description_label,
                    goatDetail = goatEntity.description ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@Composable
private fun GoatVaccinations(
    vaccinationsList: List<Vaccination>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(10.dp)
            )
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow
                ))
        ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        ExpandLabel(
            label = stringResource(R.string.vaccinations_label),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            onActionClick = {},
            imageVector = Icons.Filled.Add,
        )
        if (expanded) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            ) {
                if (vaccinationsList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.empty_vaccinations_list_label),
                           // style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                        )
                    }

                } else {
                    LazyColumn(
                        modifier = modifier,
                        contentPadding = contentPadding,
                    ) {
                        items(items = vaccinationsList, key = { it.id }) {vaccination ->
                            Row(modifier = modifier) {
                                Text(text = vaccination.date.toString())
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = vaccination.medication)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GoatSicknesses(
    sicknessesList: List<Sickness>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .border(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            shape = RoundedCornerShape(10.dp)
            )
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        ExpandLabel(
            label = stringResource(R.string.sicknesses_label),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            onActionClick = {},
            imageVector = Icons.Filled.Add,
        )
        if (expanded) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            ) {
                if (sicknessesList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.empty_sicknesses_list_label),
                            //style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                        )
                    }

                } else {
                    LazyColumn(
                        modifier = modifier,
                        contentPadding = contentPadding,
                    ) {
                        items(items = sicknessesList, key = { it.id }) { vaccination ->
                            Row(modifier = modifier) {
                                Text(text = vaccination.startDate.toString())
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = vaccination.endDate.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GoatDetailsRow(
    @StringRes labelResID: Int,
    goatDetail: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = goatDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    goatName: String,
    modifier: Modifier = Modifier,
) {//ToDo: Доделать строки
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.warning_label)) },
        text = { Text(stringResource(R.string.delete_warning, "Козу", goatName)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GoatDetailsScreenPreview() {
    AppTheme {
        GoatDetailsBody(
            GoatDetailsUiState(
                goatDetails = GoatDetails(UUID.randomUUID(), "Pen", "$100", "10")
            ), onDelete = {})
    }
}
