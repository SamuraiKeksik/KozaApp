package com.example.kozaapp.features.animals.goats.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kozaapp.R
import com.example.kozaapp.ui.NavigationDestination
import com.example.kozaapp.ui.theme.AppTheme
import kotlinx.coroutines.launch

object GoatEditDestination : NavigationDestination{
    override val route = "GoatEditScreen"
    @StringRes
    override val titleRes = R.string.empty_string
    override val showBottomBar = false
    const val goatIdArg = "goatId"
    val routeWithArgs = "$route/{$goatIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoatEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoatEditViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
    ) { 
        GoatEntryBody(
            goatUiState = viewModel.goatUiState,
            onGoatValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateGoat()
                    navigateBack()
                }
            },
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GoatEditScreenPreview() {
    AppTheme {
        GoatEditScreen(navigateBack = { /*Do nothing*/ }, )
    }
}
