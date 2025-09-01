package com.example.kozaapp.auth.data

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kozaapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.Boolean

class AuthViewModel : ViewModel() {
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
    var name by mutableStateOf("")
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

    fun updateName(newName: String) {
        name = newName
    }

    fun updatePhone(newPhone: String) {
        phone = newPhone
    }

    fun updateAddress(newAddress: String) {
        address = newAddress
    }

    fun isEmailValidForRegistration(): Boolean {
        if (!isEmailValid()) {
            updateUiStateError(
                error = R.string.email_validation_error,
                isEmailWrong = true,
            )
            return false
        }
        else if (isEmailRegistered()) {
            updateUiStateError(
                error = R.string.email_occupied_error,
                isEmailWrong = true,
            )
            return false
        }
        else {
            resetUiStateError()
            return true
        }
    }

    fun isEmailValidForPasswordRecovery(): Boolean {
        if (!isEmailValid()) {
            updateUiStateError(
                error = R.string.email_validation_error,
                isEmailWrong = true,
            )
            return false
        }
        else if (!isEmailRegistered()) {
            updateUiStateError(
                error = R.string.email_not_registered_error,
                isEmailWrong = true,
            )
            return false
        }
        else {
            resetUiStateError()
            return true
        }
    }

    fun isEmailValidForLogin(): Boolean {
        if (isEmailValidForPasswordRecovery())
            return true
        else
            return false
    }

    private fun isEmailValid(): Boolean{
        val emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()
        if (!emailRegex.matches(email))
            return false
        else
            return true
    }
    fun isEmailRegistered(): Boolean {
            return true
        //ToDo: Сделать проверку через сервер
    }

    fun isPasswordValid(): Boolean {
        if (password.length < 8) {
            updateUiStateError(
                error = R.string.password_validation_error,
                isPasswordWrong = true,
            )
            return false
        } else {
            resetUiStateError()
            return true
        }
    }

    fun tryToRegister():Boolean {
        //ToDo: Сделать запрос на регистрацию на сервер
        return true
    }

    fun tryToLogin():Boolean{
        if (isEmailValidForLogin() && isPasswordValid())
            return true
        else
            return false
        //ToDo: Сделать запрос на логин на сервер

    }

    fun tryToRecoverPassword():Boolean{
        if (isEmailValidForPasswordRecovery())
            return true
        else
            return false
        //ToDo: Сделать запрос на отправку кода подтверждения на сервер
    }

    fun tryToChangePassword():Boolean{
        if (isPasswordValid()){
            if (password != passwordConfirmation) {
                updateUiStateError(
                    error = R.string.password_confirmation_validation_error,
                    isPasswordConfirmationWrong = true,
                )
                return false
            }
            return true
        }
        else
            return false

        //ToDo: Сделать запрос на смену пароля на сервер
    }

    fun isConfirmationCodeValid(): Boolean {
        //ToDo: Сделать запрос на проверку кода на серверы
        return true
    }

    fun resetUiStateError(){
        updateUiStateError()
    }
    private fun updateUiStateError(
        isLoading: Boolean = false,
        @StringRes error: Int? = null,
        isEmailWrong: Boolean = false,
        isPasswordWrong: Boolean = false,
        isPasswordConfirmationWrong: Boolean = false,
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = isLoading,
                error = error,
                isEmailWrong = isEmailWrong,
                isPasswordWrong = isPasswordWrong,
                isPasswordConfirmationWrong = isPasswordConfirmationWrong,
            )
        }
    }




}