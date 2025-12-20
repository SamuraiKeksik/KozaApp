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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_data.animals.Sickness
import com.example.app_data.animals.SicknessType
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.goats.GoatEntity
import com.example.app_features.DatePickerModal
import com.example.app_features.ExpandLabel
import com.example.app_features.R
import com.example.app_features.theme.AppTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoatDetailsScreen(
    navigateToEditGoat: (UUID) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoatDetailsViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
    ) {
        GoatDetailsBody(
            viewModel = viewModel,
            navigateBack = navigateBack,
            navigateToEditGoat = navigateToEditGoat,
//
        )//
    }
}

@Composable
private fun GoatDetailsBody(
    viewModel: GoatDetailsViewModel,
    navigateToEditGoat: (UUID) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsState().value
    val sicknessTypesList = viewModel.sicknessTypesList.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var addVaccinationRequired by rememberSaveable { mutableStateOf(false) }
    var addSicknessRequired by rememberSaveable { mutableStateOf(false) }
    var dateSelectionRequired by rememberSaveable { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        GoatDetails(
            goatEntity = uiState.goatDetails.toGoat(),
            onActionClick = { navigateToEditGoat(uiState.goatDetails.id) }
        )
        GoatVaccinations(
            vaccinationsList = uiState.goatVaccinations,
            onAddClick = {
                addVaccinationRequired = true
            }
        )
        GoatSicknesses(
            sicknessesList = uiState.goatSicknesses,
            onAddClick = { addSicknessRequired = true }
        )

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
                    coroutineScope.launch {
                        viewModel.deleteGoat()
                        navigateBack()
                    }
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                goatName = uiState.goatDetails.name,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        if (addVaccinationRequired) {
            AddVaccinationDialog(
                vaccinationDetails = viewModel.vaccinationUiState.vaccinationDetails,
                isEntryValid = viewModel.vaccinationUiState.isEntryValid,
                onAddConfirm = {
                    coroutineScope.launch {
                        viewModel.insertVaccination()
                    }
                    addVaccinationRequired = false

                },
                onAddCancel = { addVaccinationRequired = false },
                onDateFocused = { dateSelectionRequired = true },
                onDateUnFocused = { dateSelectionRequired = false },
                onValueChange = { viewModel.updateVaccinationUiState(it) },
                sicknessTypesList = sicknessTypesList.sicknessTypesList,
                dateFormat = dateFormat,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        if (addSicknessRequired) {
            AddSicknessDialog(
                onAddConfirm = {
                    addSicknessRequired = false
                    coroutineScope.launch {
                        //viewModel.insertSickness()
                    }
                },
                onAddCancel = { addSicknessRequired = false },
                goatName = "goatDetailsUiState.goatDetails.name",
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
        if (dateSelectionRequired) {
            DatePickerModal(
                onDateSelected = {
                    viewModel.updateVaccinationUiState(
                        vaccinationDetails = it.let {
                            viewModel.vaccinationUiState.vaccinationDetails.copy(
                                date = it!!
                            )
                        }
                    )
                    dateSelectionRequired = false
                },
                onDismiss = {
                    dateSelectionRequired = false
                }
            )
        }
    }
}


@Composable
fun GoatDetails(
    goatEntity: GoatEntity,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(true) }
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
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        ExpandLabel(
            label = stringResource(R.string.goat_details_screen_label),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            onActionClick = onActionClick,
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
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_gender_label,
                    goatDetail = goatEntity.gender.toString(),
                    //goatDetail = stringResource(goat.gender.labelResId),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.breed_label,
                    goatDetail = goatEntity.breed.toString(),
                    //goatDetail = stringResource(goat.breed.labelResId),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
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
    onAddClick: () -> Unit,
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
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        ExpandLabel(
            label = stringResource(R.string.vaccinations_label),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            onActionClick = onAddClick,
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
                        items(items = vaccinationsList, key = { it.id }) { vaccination ->
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
    onAddClick: () -> Unit,
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
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        ExpandLabel(
            label = stringResource(R.string.sicknesses_label),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            onActionClick = onAddClick,
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
private fun AddVaccinationDialog(
    vaccinationDetails: VaccinationDetails,
    sicknessTypesList: List<SicknessType>,
    isEntryValid: Boolean,
    onAddConfirm: () -> Unit,
    onAddCancel: () -> Unit,
    onDateFocused: () -> Unit,
    onDateUnFocused: () -> Unit,
    onValueChange: (VaccinationDetails) -> Unit,
    dateFormat: SimpleDateFormat,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = { /* Do nothing */ }) {
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(R.string.add_vaccination_label),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    modifier = Modifier.padding(0.dp),
                    onClick = { onAddCancel() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        dimensionResource(id = R.dimen.padding_medium),
                        0.dp,
                        dimensionResource(id = R.dimen.padding_medium),
                        dimensionResource(id = R.dimen.padding_medium),
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column {
                    OutlinedTextField(
                        value = dateFormat.format(vaccinationDetails.date),
                        onValueChange = { onValueChange(vaccinationDetails.copy(date = it.toLong())) },
                        label = { Text(stringResource(R.string.date)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) onDateFocused() else onDateUnFocused()
                            },
                        readOnly = true,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        trailingIcon = {
                            IconButton(onClick = onDateFocused) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = null
                                )
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    SicknessTypeSelector(
                        sicknessTypesList = sicknessTypesList,
                        selectedSicknessName = vaccinationDetails.sicknessName,
                        onSicknessSelected = {
                            onValueChange(
                                vaccinationDetails.copy(
                                    sicknessTypeId = it,
                                    sicknessName = sicknessTypesList.first { s -> s.id == it }.name
                                )
                            )
                        },
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    OutlinedTextField(
                        value = vaccinationDetails.medication,
                        onValueChange = { onValueChange(vaccinationDetails.copy(medication = it)) },
                        label = { Text(stringResource(R.string.medication)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onAddCancel() },
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                    TextButton(
                        onClick = { onAddConfirm() },
                        enabled = isEntryValid,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                    ) {
                        Text(stringResource(R.string.add))
                    }
                }
            }
        }
    }
}

@Composable
private fun AddSicknessDialog(
    onAddConfirm: () -> Unit,
    onAddCancel: () -> Unit,
    goatName: String,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.add_sickness_label)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onAddCancel) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = onAddConfirm) {
                Text(text = stringResource(R.string.add))
            }
        })
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    goatName: String,
    modifier: Modifier = Modifier,
) {//ToDo: Доделать строки
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SicknessTypeSelector(
    sicknessTypesList: List<SicknessType>,
    selectedSicknessName: String,
    onSicknessSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedSicknessName,
            onValueChange = { /* Не изменяем вручную, только через меню */ },
            readOnly = true,
            label = { Text(stringResource(R.string.sickness)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sicknessTypesList.forEach { sicknessType ->
                DropdownMenuItem(
                    text = { Text(sicknessType.name) },
                    //text = { Text(stringResource(breed.labelResId)) },
                    onClick = {
                        onSicknessSelected(sicknessType.id)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GoatDetailsScreenPreview() {
    AppTheme {
//        GoatDetailsBody(
//            viewModel = GoatDetailsViewModel(),
//            navigateToEditGoat = {},
//            navigateBack = {}
//        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddVaccinationDialogPreview() {
    AppTheme {
//        AddVaccinationDialog(
//            onAddConfirm = {},
//            onAddCancel = {},
//            onDateFocused = {},
//            onDateUnFocused = {},
//            goatName = "Pen",
//        )
    }
}
