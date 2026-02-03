package com.example.app_features.animals.chickens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.Gender
import com.example.app_data.animals.chickens.ChickenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChickenAddParentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chickenRepository: ChickenRepository,
) : ViewModel() {
    private val chickenId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))
    private val parentGender: Gender = Gender.valueOf(checkNotNull(savedStateHandle["parent_gender"]))

    var uiState: ChickenParentsUiState by mutableStateOf(ChickenParentsUiState())
        private set

    val chickensUiState: StateFlow<ChickensUiState> =
        chickenRepository.chickensList.map { ChickensUiState(
            chickensList = it.filter { entity ->  entity.id != chickenId && entity.gender == parentGender }, isLoading = false) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ChickensUiState(),
            )

    fun updateParentId(parentId: UUID) {
        uiState = ChickenParentsUiState(
            selectedParentId = parentId,
            isLoading = false
        )
    }

    suspend fun saveParent() {
        var chicken = chickenRepository.getChickenModel(chickenId).first()?.chicken
            ?: return

        if (uiState.selectedParentId == null) return

        if (parentGender == Gender.FEMALE){
            chicken = chicken.copy(motherId = uiState.selectedParentId)
        }
        else{
            chicken = chicken.copy(fatherId = uiState.selectedParentId)
        }

        chickenRepository.updateChicken(chicken)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ChickenParentsUiState(
    val selectedParentId: UUID? = null,
    val isLoading: Boolean = true,
)

