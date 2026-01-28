package com.example.app_features.animals.goats

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_data.animals.goats.Breed
import com.example.app_data.animals.goats.Gender
import com.example.app_data.animals.goats.Status
import com.example.app_features.R
import com.example.app_features.theme.AppTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

//object GoatEntryDestination : NavigationDestination{
//    override val route = "GoatEntryScreen"
//    @StringRes
//    override val titleRes = R.string.goat_entry_screen_label
//    override val showBottomBar = false
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoatEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: GoatEntryViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        GoatEntryBody(
            goatUiState = viewModel.goatUiState,
            onGoatValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
        )
    }
}

@Composable
fun GoatEntryBody(
    goatUiState: GoatUiState,
    onGoatValueChange: (GoatDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        GoatInputForm(
            goatDetails = goatUiState.goatDetails,
            onValueChange = onGoatValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { onSaveClick() },
            enabled = goatUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.goat_save_button_label))
        }
    }
}

@Composable
fun GoatInputForm(
    goatDetails: GoatDetails,
    modifier: Modifier = Modifier,
    onValueChange: (GoatDetails) -> Unit = {},
    enabled: Boolean = true
) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = goatDetails.name,
            onValueChange = { onValueChange(goatDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.goat_name_label)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )
        Row(){
            GenderSelector(
                modifier = Modifier.weight(1f),
                selectedGender = goatDetails.toGoat().gender,
                onGenderSelected = { newGender ->
                    onValueChange(goatDetails.copy(gender = newGender))
                },
            )
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_extra_small)))
            BreedSelector(
                modifier = Modifier.weight(1f),
                selectedBreed = goatDetails.toGoat().breed,
                onBreedSelected = { newBreed ->
                    onValueChange(goatDetails.copy(breed = newBreed))
                },
            )
        }
        Row(){
            StatusSelector(
                modifier = Modifier.weight(1f),
                selectedStatus = goatDetails.toGoat().status,
                onStatusSelected = { newStatus ->
                    onValueChange(goatDetails.copy(status = newStatus))
                },
            )
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_extra_small)))
            OutlinedTextField(
                value = goatDetails.weight,
                onValueChange = { onValueChange(goatDetails.copy(weight = it)) },
                label = { Text(stringResource(R.string.goat_weight_label)) },
                modifier = Modifier.weight(1f),
                enabled = enabled,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }

        DatePickerField(
            label = "Дата рождения",
            date = goatDetails.birthDate.format(formatter) ?: "",
            onDateSelected = { onValueChange(goatDetails.copy(birthDate = LocalDate.parse(it, formatter))) },
            allowEmpty = false
        )
        OutlinedTextField(
            value = goatDetails.description,
            onValueChange = { onValueChange(goatDetails.copy(description = it)) },
            label = { Text(stringResource(R.string.goat_description_label)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
        )
        if (enabled) {
            Text(
                text = stringResource(R.string.goat_required_fields_label),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderSelector(
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = stringResource(selectedGender.valueRes),
            onValueChange = { /* Не изменяем вручную, только через меню */ },
            readOnly = true,
            singleLine = true,
            label = { Text(stringResource(R.string.gender_label)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Gender.valuesList().forEach { gender ->
                DropdownMenuItem(
                    text = { Text(stringResource(gender.valueRes)) },
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedSelector(
    selectedBreed: Breed,
    onBreedSelected: (Breed) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = stringResource(selectedBreed.valueRes),
            onValueChange = { /* Не изменяем вручную, только через меню */ },
            readOnly = true,
            singleLine = true,
            label = { Text(stringResource(R.string.breed_label)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Breed.valuesList().forEach { breed ->
                DropdownMenuItem(
                    text = { Text(stringResource(breed.valueRes)) },
                    //text = { Text(stringResource(breed.labelResId)) },
                    onClick = {
                        onBreedSelected(breed)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusSelector(
    selectedStatus: Status,
    onStatusSelected: (Status) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = stringResource(selectedStatus.valueRes),
            onValueChange = { /* Не изменяем вручную, только через меню */ },
            readOnly = true,
            singleLine = true,
            label = { Text(stringResource(R.string.status_label)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Status.valuesList().forEach { status ->
                DropdownMenuItem(
                    text = { Text(stringResource(status.valueRes)) },
                    onClick = {
                        onStatusSelected(status)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    date: String,
    onDateSelected: (String) -> Unit,
    allowEmpty: Boolean = false,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    if (date.isNotBlank()) {
        try {
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val parsedDate = sdf.parse(date)
            if (parsedDate != null) calendar.time = parsedDate
        } catch (_: Exception) {}
    }

    var showDialog by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = date,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Выбрать дату",
                modifier = Modifier.clickable { showDialog = true }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    )

    if (showDialog) {
        DatePickerDialog(
            context,
            { _, year: Int, month: Int, dayOfMonth: Int ->
                val selectedCal = calendar
                selectedCal.set(year, month, dayOfMonth)
                val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(selectedCal.time)
                onDateSelected(formattedDate)
                showDialog = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            if (allowEmpty) {
                setButton(DatePickerDialog.BUTTON_NEGATIVE, "Очистить") { _, _ ->
                    onDateSelected("")
                    showDialog = false
                }
            }
            setButton(DatePickerDialog.BUTTON_NEUTRAL, "Отмена") { _, _ ->
                showDialog = false
            }
            show()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GoatEntryScreenPreview() {
    AppTheme {
        GoatEntryBody(goatUiState = GoatUiState(
            GoatDetails(
                name = "Goat name",
                gender = Gender.FEMALE,
                birthDate = LocalDate.now(),
                description = "Description"
            )
        ), onGoatValueChange = {}, onSaveClick = {})
    }
}