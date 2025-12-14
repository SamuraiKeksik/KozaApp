package com.example.app_features.animals.goats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_features.theme.AppTheme
import kotlinx.coroutines.launch

//object GoatEditDestination : NavigationDestination{
//    override val route = "GoatEditScreen"
//    @StringRes
//    override val titleRes = R.string.goat_edit_screen_label
//    override val showBottomBar = false
//    const val goatIdArg = "goatId"
//    val routeWithArgs = "$route/{$goatIdArg}"
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoatEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoatEditViewModel = hiltViewModel()
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
                    viewModel.updateGoat()
                    navigateBack()
                }
            },
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
