package com.example.app_features.monthPicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.app_features.vaccinationsCalendar.shouldUseVertical
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthPickerBottomSheet(
    showYear: Boolean = false,
    title: String = if (showYear) "Выберите месяц и год" else "Select a month",
    cancelButtonText: String = "Отменить",
    okButtonText: String = "Ok",
    description: String = "",
    onDismiss: () -> Unit,
    onUpdateMonth: (Int, Int) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
        ModalBottomSheet(
            onDismissRequest = {
                onDismiss()
            }, sheetState = sheetState
        ) {
            Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MonthPickerUI(
                isDialog = false,
                showYear,
                title,
                cancelButtonText,
                okButtonText,
                description,
                onCancel = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
                },
                onUpdateMonth = { month, year ->
                    coroutineScope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onUpdateMonth(month, year)
                        }
                    }
                })
        }
    }
}