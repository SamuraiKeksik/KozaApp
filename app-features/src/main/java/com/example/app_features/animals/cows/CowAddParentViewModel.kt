package com.example.app_features.animals.cows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.Gender
import com.example.app_data.animals.cows.CowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CowAddParentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cowRepository: CowRepository,
) : ViewModel() {
    private val cowId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))
    private val parentGender: Gender = Gender.valueOf(checkNotNull(savedStateHandle["parent_gender"]))

    var uiState: CowParentsUiState by mutableStateOf(CowParentsUiState())
        private set

    val cowsUiState: StateFlow<CowsUiState> =
        cowRepository.cowsList.map { CowsUiState(
            cowsList = it.filter { entity ->  entity.id != cowId && entity.gender == parentGender }, isLoading = false) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CowsUiState(),
            )

    fun updateParentId(parentId: UUID) {
        uiState = CowParentsUiState(
            selectedParentId = parentId,
            isLoading = false
        )
    }

    suspend fun saveParent() {
        var cow = cowRepository.getCowModel(cowId).first()?.cow
            ?: return

        if (uiState.selectedParentId == null) return

        if (parentGender == Gender.FEMALE){
            cow = cow.copy(motherId = uiState.selectedParentId)
        }
        else{
            cow = cow.copy(fatherId = uiState.selectedParentId)
        }

        cowRepository.updateCow(cow)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class CowParentsUiState(
    val selectedParentId: UUID? = null,
    val isLoading: Boolean = true,
)

