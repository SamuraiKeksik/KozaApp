package com.example.app_features.animals.chickens

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

//object ChickenEditDestination : NavigationDestination{
//    override val route = "ChickenEditScreen"
//    @StringRes
//    override val titleRes = R.string.chicken_edit_screen_label
//    override val showBottomBar = false
//    const val chickenIdArg = "chickenId"
//    val routeWithArgs = "$route/{$chickenIdArg}"
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChickenEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChickenEditViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        ChickenEntryBody(
            chickenUiState = viewModel.chickenUiState,
            onChickenValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateChicken()
                    navigateBack()
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChickenEditScreenPreview() {
    AppTheme {
        ChickenEditScreen(navigateBack = { /*Do nothing*/ }, )
    }
}
