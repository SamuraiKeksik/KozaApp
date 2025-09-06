package com.example.kozaapp.auth.data

import androidx.annotation.StringRes

data class AuthUiState (
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null,
    val isEmailWrong: Boolean = false,
    val isNicknameWrong: Boolean = false,
    val isPasswordWrong: Boolean = false,
    val isPasswordConfirmationWrong: Boolean = false,
)