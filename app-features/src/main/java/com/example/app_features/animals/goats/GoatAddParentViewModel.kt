package com.example.app_features.animals.goats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.goats.Gender
import com.example.app_data.animals.goats.GoatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GoatAddParentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val goatRepository: GoatRepository,
) : ViewModel() {
    private val goatId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))
    private val parentGender: Gender = Gender.valueOf(checkNotNull(savedStateHandle["parent_gender"]))

    var uiState: GoatParentsUiState by mutableStateOf(GoatParentsUiState())
        private set

    val goatsUiState: StateFlow<GoatsUiState> =
        goatRepository.goatsList.map { GoatsUiState(
            goatsList = it.filter { entity ->  entity.id != goatId && entity.gender == parentGender }, isLoading = false) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GoatsUiState(),
            )

    fun updateParentId(parentId: UUID) {
        uiState = GoatParentsUiState(
            selectedParentId = parentId,
            isLoading = false
        )
    }

    suspend fun saveParent() {
        var goat = goatRepository.getGoat(goatId).first()?.goat
            ?: return

        if (uiState.selectedParentId == null) return

        if (parentGender == Gender.FEMALE){
            goat = goat.copy(motherId = uiState.selectedParentId)
        }
        else{
            goat = goat.copy(fatherId = uiState.selectedParentId)
        }

        goatRepository.updateGoat(goat)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GoatParentsUiState(
    val selectedParentId: UUID? = null,
    val isLoading: Boolean = true,
)

