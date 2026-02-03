package com.example.app_features.animals.goats

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_data.animals.goats.Gender
import com.example.app_features.DatePickerModal
import com.example.app_features.R
import com.example.app_features.animals.AnimalMilkYields
import com.example.app_features.animals.AnimalSicknesses
import com.example.app_features.animals.AnimalVaccinations
import com.example.app_features.animals.DeleteConfirmationDialog
import com.example.app_features.animals.MilkYieldDialog
import com.example.app_features.animals.SicknessDialog
import com.example.app_features.animals.VaccinationDialog
import com.example.app_features.theme.AppTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoatDetailsScreen(
    navigateToEditGoat: (UUID) -> Unit,
    navigateToAddParent: (UUID, Gender) -> Unit,
    navigateToParentInfo: (UUID) -> Unit,
    navigateToChildren: (UUID) -> Unit,
    navigateBack: () -> Unit,
    canEdit: Boolean = true,
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
            navigateToAddParent = navigateToAddParent,
            navigateToParentInfo = navigateToParentInfo,
            navigateToChildren = navigateToChildren,
            canEdit = canEdit,
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun GoatDetailsBody(
    viewModel: GoatDetailsViewModel,
    navigateToEditGoat: (UUID) -> Unit,
    navigateToAddParent: (UUID, Gender) -> Unit,
    navigateToParentInfo: (UUID) -> Unit,
    navigateToChildren: (UUID) -> Unit,
    navigateBack: () -> Unit,
    canEdit: Boolean,
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
    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    //val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        GoatDetailsComposable(
            goatDetails = uiState.goatDetails,
            onEditClick = { navigateToEditGoat(uiState.goatDetails.id) },
            onParentInfoClick = navigateToParentInfo,
            onParentAddClick = { id, gender ->
                navigateToAddParent(id, gender)
            },
            onChildrenInfoClick = navigateToChildren,
            canEdit = canEdit
        )
        AnimalVaccinations(
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
            dateFormat = dateFormat,
            canEdit = canEdit
        )
        AnimalSicknesses(
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
            dateFormat = dateFormat,
            canEdit = canEdit
        )
    if (uiState.goatDetails.gender == Gender.FEMALE){
        AnimalMilkYields(
            milkYieldsList = uiState.goatMilkYields,
            onAddClick = {
                viewModel.clearMilkYieldUiState()
                addMilkYieldRequired = true
            },
            onEditClick = {
                coroutineScope.launch {
                    viewModel.getMilkYield(id = it)
                }
                editMilkYieldRequired = true
            },
            onDeleteClick = {
                viewModel.updateMilkYieldUiState(
                    viewModel.milkYieldUiState.milkYieldDetails.copy(id = it)
                )
                deleteMilkYieldConfirmationRequired = true
            },
            dateFormat = dateFormat,
            canEdit = canEdit
        )
    }
        if(canEdit) {
            OutlinedButton(
                onClick = { deleteConfirmationRequired = true },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.goat_delete_button_label))
            }
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

        if (editMilkYieldRequired) {
            MilkYieldDialog(
                label = stringResource(R.string.edit_milk_yield_label),
                milkYieldDetails = viewModel.milkYieldUiState.milkYieldDetails,
                isEntryValid = viewModel.milkYieldUiState.isEntryValid,
                onAddConfirm = {
                    coroutineScope.launch {
                        viewModel.updateMilkYield()
                    }
                    editMilkYieldRequired = false

                },
                onAddCancel = { editMilkYieldRequired = false },
                onDateFocused = { milkYieldDateSelectionRequired = true },
                onDateUnFocused = { milkYieldDateSelectionRequired = false },
                onValueChange = { viewModel.updateMilkYieldUiState(it) },
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
                            val dateTime = it!!.toEpochDay()
                            viewModel.vaccinationUiState.vaccinationDetails.copy(
                                date = dateTime
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
                            val dateTime = it!!.toEpochDay()
                            viewModel.sicknessUiState.sicknessDetails.copy(
                                startDate = dateTime
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
                            val dateTime = it!!.toEpochDay()
                            viewModel.sicknessUiState.sicknessDetails.copy(
                                endDate = dateTime
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
        if (milkYieldDateSelectionRequired) {
            DatePickerModal(
                onDateSelected = {
                    viewModel.updateMilkYieldUiState(
                        milkYieldDetails = it.let {
                            val dateTime = it!!.toEpochDay()
                            viewModel.milkYieldUiState.milkYieldDetails.copy(
                                date = dateTime
                            )
                        }
                    )
                    milkYieldDateSelectionRequired = false
                },
                onDismiss = {
                    milkYieldDateSelectionRequired = false
                }
            )
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
