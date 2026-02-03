package com.example.app_features.animals.cows

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

//object CowEditDestination : NavigationDestination{
//    override val route = "CowEditScreen"
//    @StringRes
//    override val titleRes = R.string.cow_edit_screen_label
//    override val showBottomBar = false
//    const val cowIdArg = "cowId"
//    val routeWithArgs = "$route/{$cowIdArg}"
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CowEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CowEditViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        CowEntryBody(
            cowUiState = viewModel.cowUiState,
            onCowValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateCow()
                    navigateBack()
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CowEditScreenPreview() {
    AppTheme {
        CowEditScreen(navigateBack = { /*Do nothing*/ }, )
    }
}
