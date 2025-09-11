package com.example.kozaapp.auth.data

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kozaapp.R
import com.example.kozaapp.data.AuthRepository
import com.example.kozaapp.data.network.RegistrationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordConfirmation by mutableStateOf("")
        private set
    var confirmationCode by mutableStateOf("")
        private set
    var nickname by mutableStateOf("")
        private set
    var phone by mutableStateOf("")
        private set
    var address by mutableStateOf("")
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    fun updatePasswordConfirmation(newPasswordConfirmation: String) {
        passwordConfirmation = newPasswordConfirmation
    }

    fun updateConfirmationCode(newConfirmationCode: String) {
        confirmationCode = newConfirmationCode
    }

    fun updateNickname(newNickname: String) {
        nickname = newNickname
    }

    fun updatePhone(newPhone: String) {
        phone = newPhone
    }

    fun updateAddress(newAddress: String) {
        address = newAddress
    }

    fun isEmailValid(): Boolean {
        val emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()
        if (!emailRegex.matches(email)) {
            updateUiState(
                isEmailWrong = true,
                error = R.string.email_validation_error,
            )
            return false
        } else {
            resetUiStateError()
            return true
        }

    }

    fun isEmailRegistered(): Boolean {
        return true
        //ToDo: Сделать проверку через сервер
    }

    fun isPasswordValid(): Boolean {
        if (password.length < 8) {
            updateUiState(
                error = R.string.password_validation_error,
                isPasswordWrong = true,
            )
            return false
        } else {
            resetUiStateError()
            return true
        }
    }


    fun isNicknameValid(): Boolean {
        if (nickname.length < 3) {
            updateUiState(
                isNicknameWrong = true,
                error = R.string.nickname_validation_error
            )
            return false
        } else {
            resetUiStateError()
            return true
        }
    }

    fun tryToLogin() {
        if (isNicknameValid() && isPasswordValid()) {
            viewModelScope.launch {
                updateUiState(isLoading = true)
                val result = authRepository.login(nickname, password)
                if (result == true) {
                    updateUiState(isLoginSuccess = true)
                }
            }
        }
        //ToDo: Сделать обработку при исключении

    }

    fun tryToRegister() {
        if (isEmailValid() && isPasswordValid() && password == passwordConfirmation) {
            val request = RegistrationRequest(
                email = email,
                password = password,
                username = nickname,
                phone = phone,
                region = address,
            )
            viewModelScope.launch {
                updateUiState(isLoading = true)
                val result = authRepository.registration(request)
                if (result == true) {
                    updateUiState(isRegistrationSuccess = true)
                }
            }
        }
        //ToDo: Сделать обработку при исключении
    }

    fun tryToRecoverPassword(): Boolean {
        if (isEmailValid())
            return true
        else
            return false
        //ToDo: Сделать запрос на отправку кода подтверждения на сервер
    }

    fun tryToChangePassword(): Boolean {
        if (isPasswordValid()) {
            if (password != passwordConfirmation) {
                updateUiState(
                    error = R.string.password_confirmation_validation_error,
                    isPasswordConfirmationWrong = true,
                )
                return false
            }
            return true
        } else
            return false

        //ToDo: Сделать запрос на смену пароля на сервер
    }

    fun isConfirmationCodeValid(): Boolean {
        //ToDo: Сделать запрос на проверку кода на серверы
        return true
    }

    fun resetUiStateError() {
        updateUiState()
    }

    private fun updateUiState(
        isLoading: Boolean = false,
        isLoginSuccess: Boolean = false,
        isRegistrationSuccess: Boolean = false,
        @StringRes error: Int? = null,
        isEmailWrong: Boolean = false,
        isNicknameWrong: Boolean = false,
        isPasswordWrong: Boolean = false,
        isPasswordConfirmationWrong: Boolean = false,
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = isLoading,
                isLoginSuccess = isLoginSuccess,
                isRegistrationSuccess = isRegistrationSuccess,
                error = error,
                isEmailWrong = isEmailWrong,
                isNicknameWrong = isNicknameWrong,
                isPasswordWrong = isPasswordWrong,
                isPasswordConfirmationWrong = isPasswordConfirmationWrong,
            )
        }
    }
}