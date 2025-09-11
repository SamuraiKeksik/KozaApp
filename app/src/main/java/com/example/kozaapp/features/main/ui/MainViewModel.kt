package com.example.kozaapp.features.main.ui

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kozaapp.data.AnimalsRepository
import com.example.kozaapp.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val animalsRepository: AnimalsRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination.asStateFlow()

    init {
        checkApiConnection()
    }

    fun checkApiConnection(){
        viewModelScope.launch {
            val result = animalsRepository.getCurrentUser()
            if (result != null){
                _startDestination.value = MainScreensEnum.MainScreen.name
                Log.d("MyNetwork", "Успешная проверка доступа к api")
            }
            else{
                _startDestination.value = MainScreensEnum.AuthScreen.name
                Log.d("MyNetwork", "Неудачная проверка доступа к api")
            }
        }//ToDo: сделать обработку ошибок
    }
    private fun updateUiState(
        isLoggedIn: Boolean = false,
    ){
        _uiState.update { currentState ->
            currentState.copy(
                isLoggedIn = isLoggedIn,
            )
        }
    }
}