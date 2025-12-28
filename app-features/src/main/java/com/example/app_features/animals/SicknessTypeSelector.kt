package com.example.app_features.animals

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.app_data.animals.SicknessType
import com.example.app_features.R

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
