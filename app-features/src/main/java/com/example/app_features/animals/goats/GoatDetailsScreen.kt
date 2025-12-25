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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_data.animals.MilkYield
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
    var deleteVaccinationConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var deleteSicknessConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var deleteMilkYieldConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    var addVaccinationRequired by rememberSaveable { mutableStateOf(false) }
    var addSicknessRequired by rememberSaveable { mutableStateOf(false) }
    var addMilkYieldRequired by rememberSaveable { mutableStateOf(false) }

    var editVaccinationRequired by rememberSaveable { mutableStateOf(false) }
    var editSicknessRequired by rememberSaveable { mutableStateOf(false) }
    var editMilkYieldRequired by rememberSaveable { mutableStateOf(false) }

    var dateSelectionRequired by rememberSaveable { mutableStateOf(false) }
    var milkYieldDateSelectionRequired by rememberSaveable { mutableStateOf(false) }
    var startDateSelectionRequired by rememberSaveable { mutableStateOf(false) }
    var endDateSelectionRequired by rememberSaveable { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        GoatDetails(
            goatEntity = uiState.goatDetails.toGoat(),
            onActionClick = { navigateToEditGoat(uiState.goatDetails.id) }
        )
        GoatVaccinations(
            vaccinationsList = uiState.goatVaccinations,
            sicknessTypesList = sicknessTypesList.sicknessTypesList,
            onAddClick = {
                viewModel.clearVaccinationUiState()
                addVaccinationRequired = true
            },
            onEditClick = {
                coroutineScope.launch {
                    viewModel.getVaccination(id = it)
                }
                editVaccinationRequired = true
            },
            onDeleteClick = {
                viewModel.updateVaccinationUiState(
                    viewModel.vaccinationUiState.vaccinationDetails.copy(id = it)
                )
                deleteVaccinationConfirmationRequired = true
            },
            dateFormat = dateFormat
        )
        GoatSicknesses(
            sicknessesList = uiState.goatSicknesses,
            sicknessTypesList = sicknessTypesList.sicknessTypesList,
            onAddClick = {
                viewModel.clearVaccinationUiState()
                addSicknessRequired = true
            },
            onEditClick = {
                coroutineScope.launch {
                    viewModel.getSickness(id = it)
                }
                editSicknessRequired = true
            },
            onDeleteClick = {
                viewModel.updateSicknessUiState(
                    viewModel.sicknessUiState.sicknessDetails.copy(id = it)
                )
                deleteSicknessConfirmationRequired = true
            },
            dateFormat = dateFormat
        )

        GoatMilkYields(
            milkYieldsList = uiState.goatMilkYields,
            onAddClick = {
                viewModel.clearMilkYieldUiState()
                addMilkYieldRequired = true
            },
            onEditClick = {
                coroutineScope.launch {
                    viewModel.getSickness(id = it)
                }
                editMilkYieldRequired = true
            },
            onDeleteClick = {
                viewModel.updateMilkYieldUiState(
                    viewModel.milkYieldUiState.milkYieldDetails.copy(id = it)
                )
                deleteMilkYieldConfirmationRequired = true
            },
            dateFormat = dateFormat
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
                warningText = stringResource(
                    R.string.delete_warning,
                    "Козу",
                    uiState.goatDetails.name
                ),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        if (addVaccinationRequired) {
            VaccinationDialog(
                label = stringResource(R.string.add_vaccination_label),
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

        if (editVaccinationRequired) {
            VaccinationDialog(
                label = stringResource(R.string.edit_vaccination_label),
                vaccinationDetails = viewModel.vaccinationUiState.vaccinationDetails,
                isEntryValid = viewModel.vaccinationUiState.isEntryValid,
                onAddConfirm = {
                    coroutineScope.launch {
                        viewModel.updateVaccination()
                    }
                    editVaccinationRequired = false

                },
                onAddCancel = { editVaccinationRequired = false },
                onDateFocused = { dateSelectionRequired = true },
                onDateUnFocused = { dateSelectionRequired = false },
                onValueChange = { viewModel.updateVaccinationUiState(it) },
                sicknessTypesList = sicknessTypesList.sicknessTypesList,
                dateFormat = dateFormat,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        if (editSicknessRequired) {
            SicknessDialog(
                label = stringResource(R.string.edit_sickness_label),
                sicknessDetails = viewModel.sicknessUiState.sicknessDetails,
                isEntryValid = viewModel.sicknessUiState.isEntryValid,
                onAddConfirm = {
                    coroutineScope.launch {
                        viewModel.updateSickness()
                    }
                    editSicknessRequired = false

                },
                onAddCancel = { editSicknessRequired = false },
                onStartDateFocused = { startDateSelectionRequired = true },
                onStartDateUnFocused = { startDateSelectionRequired = false },
                onEndDateFocused = { endDateSelectionRequired = true },
                onEndDateUnFocused = { endDateSelectionRequired = false },
                onValueChange = { viewModel.updateSicknessUiState(it) },
                sicknessTypesList = sicknessTypesList.sicknessTypesList,
                dateFormat = dateFormat,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        if (deleteVaccinationConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteVaccinationConfirmationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteVaccination()
                    }
                },
                onDeleteCancel = {
                    deleteVaccinationConfirmationRequired = false
                    viewModel.clearVaccinationUiState()
                },
                warningText = stringResource(R.string.delete_warning, "прививку", ""),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        if (deleteSicknessConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteSicknessConfirmationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteSickness()
                    }
                },
                onDeleteCancel = {
                    deleteSicknessConfirmationRequired = false
                    viewModel.clearSicknessUiState()
                },
                warningText = stringResource(R.string.delete_warning, "заболевание", ""),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        if (deleteMilkYieldConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteMilkYieldConfirmationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteMilkYield()
                    }
                },
                onDeleteCancel = {
                    deleteMilkYieldConfirmationRequired = false
                    viewModel.clearMilkYieldUiState()
                },
                warningText = stringResource(R.string.delete_warning, "удой", ""),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        if (addSicknessRequired) {
            SicknessDialog(
                label = stringResource(R.string.add_sickness_label),
                sicknessDetails = viewModel.sicknessUiState.sicknessDetails,
                isEntryValid = viewModel.sicknessUiState.isEntryValid,
                onAddConfirm = {
                    coroutineScope.launch {
                        viewModel.insertSickness()
                    }
                    addSicknessRequired = false

                },
                onAddCancel = { addSicknessRequired = false },
                onStartDateFocused = { startDateSelectionRequired = true },
                onStartDateUnFocused = { startDateSelectionRequired = false },
                onEndDateFocused = { endDateSelectionRequired = true },
                onEndDateUnFocused = { endDateSelectionRequired = false },
                onValueChange = { viewModel.updateSicknessUiState(it) },
                sicknessTypesList = sicknessTypesList.sicknessTypesList,
                dateFormat = dateFormat,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
        if (addMilkYieldRequired) {
            MilkYieldDialog(
                label = stringResource(R.string.add_sickness_label),
                milkYieldDetails = viewModel.milkYieldUiState.milkYieldDetails,
                isEntryValid = viewModel.milkYieldUiState.isEntryValid,
                onAddConfirm = {
                    coroutineScope.launch {
                        viewModel.insertMilkYield()
                    }
                    addMilkYieldRequired = false

                },
                onAddCancel = { addMilkYieldRequired = false },
                onDateFocused = { milkYieldDateSelectionRequired = true },
                onDateUnFocused = { milkYieldDateSelectionRequired = false },
                onValueChange = { viewModel.updateMilkYieldUiState(it) },
                dateFormat = dateFormat,
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
        if (startDateSelectionRequired) {
            DatePickerModal(
                onDateSelected = {
                    viewModel.updateSicknessUiState(
                        sicknessDetails = it.let {
                            viewModel.sicknessUiState.sicknessDetails.copy(
                                startDate = it!!
                            )
                        }
                    )
                    startDateSelectionRequired = false
                },
                onDismiss = {
                    startDateSelectionRequired = false
                }
            )
        }
        if (endDateSelectionRequired) {
            DatePickerModal(
                onDateSelected = {
                    viewModel.updateSicknessUiState(
                        sicknessDetails = it.let {
                            viewModel.sicknessUiState.sicknessDetails.copy(
                                endDate = it!!
                            )
                        }
                    )
                    endDateSelectionRequired = false
                },
                onDismiss = {
                    endDateSelectionRequired = false
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
    sicknessTypesList: List<SicknessType>,
    onAddClick: () -> Unit,
    onEditClick: (UUID) -> Unit,
    onDeleteClick: (UUID) -> Unit,
    dateFormat: SimpleDateFormat,
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
                        modifier = Modifier.fillMaxWidth(),
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
                        modifier = modifier.heightIn(max = 400.dp),
                        contentPadding = contentPadding,
                    ) {
                        items(items = vaccinationsList.take(10), key = { it.id }) { vaccination ->
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(modifier = Modifier) {
                                    Text(text = dateFormat.format(vaccination.date))
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                                    Text(
                                        text = sicknessTypesList.find { it.id == vaccination.sicknessTypeId }?.name
                                            ?: ""
                                    )
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                                    Text(text = vaccination.medication)
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                }
                                Row(
                                    modifier = Modifier.widthIn(min = 24.dp)
                                ) {
                                    IconButton(
                                        modifier = Modifier.size(24.dp),
                                        onClick = { onEditClick(vaccination.id) }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Mode,
                                            contentDescription = ""
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                    IconButton(
                                        modifier = Modifier.size(24.dp),
                                        onClick = { onDeleteClick(vaccination.id) }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (vaccinationsList.size > 10) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {}) { Text("Переход на экран прививок") }
                        //ToDo:Переход на экран прививок
                    }
                }
            }
        }
    }
}

@Composable
private fun GoatSicknesses(
    sicknessesList: List<Sickness>,
    sicknessTypesList: List<SicknessType>,
    onAddClick: () -> Unit,
    onEditClick: (UUID) -> Unit,
    onDeleteClick: (UUID) -> Unit,
    dateFormat: SimpleDateFormat,
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
                        modifier = Modifier.fillMaxWidth(),
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
                        modifier = modifier.heightIn(max = 400.dp),
                        contentPadding = contentPadding,
                    ) {
                        items(items = sicknessesList.take(10), key = { it.id }) { sickness ->
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(modifier = Modifier) {
                                    Text(text = dateFormat.format(sickness.startDate))
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                    Text(text = " - ")
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                    Text(
                                        text = if (sickness.endDate != null) dateFormat.format(
                                            sickness.endDate
                                        ) else ""
                                    )
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                    Text(
                                        text = sicknessTypesList.find { it.id == sickness.sicknessTypeId }?.name
                                            ?: ""
                                    )
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                }
                                Row(
                                    modifier = Modifier.widthIn(min = 24.dp)
                                ) {
                                    IconButton(
                                        modifier = Modifier.size(24.dp),
                                        onClick = { onEditClick(sickness.id) }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Mode,
                                            contentDescription = ""
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                    IconButton(
                                        modifier = Modifier.size(24.dp),
                                        onClick = { onDeleteClick(sickness.id) }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GoatMilkYields(
    milkYieldsList: List<MilkYield>,
    onAddClick: () -> Unit,
    onEditClick: (UUID) -> Unit,
    onDeleteClick: (UUID) -> Unit,
    dateFormat: SimpleDateFormat,
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
            label = stringResource(R.string.milk_yields_label),
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
                if (milkYieldsList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.empty_milk_yields_list_label),
                            //style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                        )
                    }

                } else {
                    LazyColumn(
                        modifier = modifier.heightIn(max = 400.dp),
                        contentPadding = contentPadding,
                    ) {
                        items(items = milkYieldsList.take(10), key = { it.id }) { milkYield ->
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(modifier = Modifier) {
                                    Text(text = dateFormat.format(milkYield.date))
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                    Text(text = " - ")
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                    Text(
                                        text = pluralStringResource(
                                            R.plurals.numberOfMilkYields,
                                            milkYield.amount.toInt(),
                                            String.format("%.2f", milkYield.amount)
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                }
                                Row(
                                    modifier = Modifier.widthIn(min = 24.dp)
                                ) {
                                    IconButton(
                                        modifier = Modifier.size(24.dp),
                                        onClick = { onEditClick(milkYield.id) }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Mode,
                                            contentDescription = ""
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                    IconButton(
                                        modifier = Modifier.size(24.dp),
                                        onClick = { onDeleteClick(milkYield.id) }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = ""
                                        )
                                    }
                                }
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
private fun VaccinationDialog(
    label: String,
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
                    text = label,
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
                        onSicknessUpdate = { id, name ->
                            onValueChange(
                                vaccinationDetails.copy(
                                    sicknessTypeId = id,
                                    sicknessName = name
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
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Composable
private fun SicknessDialog(
    label: String,
    sicknessDetails: SicknessDetails,
    sicknessTypesList: List<SicknessType>,
    isEntryValid: Boolean,
    onAddConfirm: () -> Unit,
    onAddCancel: () -> Unit,
    onStartDateFocused: () -> Unit,
    onStartDateUnFocused: () -> Unit,
    onEndDateFocused: () -> Unit,
    onEndDateUnFocused: () -> Unit,
    onValueChange: (SicknessDetails) -> Unit,
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
                    text = label,
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
                        value = dateFormat.format(sicknessDetails.startDate),
                        onValueChange = { onValueChange(sicknessDetails.copy(startDate = it.toLong())) },
                        label = { Text(stringResource(R.string.start_date)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) onStartDateFocused() else onStartDateUnFocused()
                            },
                        readOnly = true,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        trailingIcon = {
                            IconButton(onClick = onStartDateFocused) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = null
                                )
                            }
                        },
                    )
                    OutlinedTextField(
                        value = if (sicknessDetails.endDate == null) "" else dateFormat.format(
                            sicknessDetails.endDate
                        ),
                        onValueChange = { onValueChange(sicknessDetails.copy(endDate = it.toLongOrNull())) },
                        label = { Text(stringResource(R.string.end_date)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) onEndDateFocused() else onEndDateUnFocused()
                            },
                        readOnly = true,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        trailingIcon = {
                            IconButton(onClick = onEndDateFocused) {
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
                        selectedSicknessName = sicknessDetails.sicknessName,
                        onSicknessUpdate = { id, name ->
                            onValueChange(
                                sicknessDetails.copy(
                                    sicknessTypeId = id,
                                    sicknessName = name
                                )
                            )
                        },
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
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Composable
private fun MilkYieldDialog(
    label: String,
    milkYieldDetails: MilkYieldDetails,
    isEntryValid: Boolean,
    onAddConfirm: () -> Unit,
    onAddCancel: () -> Unit,
    onDateFocused: () -> Unit,
    onDateUnFocused: () -> Unit,
    onValueChange: (MilkYieldDetails) -> Unit,
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
                    text = label,
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
                        value = dateFormat.format(milkYieldDetails.date),
                        onValueChange = { onValueChange(milkYieldDetails.copy(date = it.toLong())) },
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
                    OutlinedTextField(
                        value = milkYieldDetails.amount.toString(),
                        onValueChange = { onValueChange(milkYieldDetails.copy(amount = it.toDoubleOrNull())) },
                        label = { Text(stringResource(R.string.litres_of_milk)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.WaterDrop,
                                contentDescription = null
                            )
                        },
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
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    warningText: String,
    modifier: Modifier = Modifier,
) {//ToDo: Доделать строки
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.warning_label)) },
        text = { Text(warningText) },
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
    onSicknessUpdate: (Int, String) -> Unit,
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
            onValueChange = {
                expanded = true
                onSicknessUpdate(0, it)
            },
            readOnly = false,
            label = { Text(stringResource(R.string.sickness_type)) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { onSicknessUpdate(0, "") }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sicknessTypesList
                .filter { it.name.contains(selectedSicknessName, ignoreCase = true) || it.id == 1 }
                .forEach { sicknessType ->
                    DropdownMenuItem(
                        text = { Text(sicknessType.name) },
                        onClick = {
                            onSicknessUpdate(sicknessType.id, sicknessType.name)
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
