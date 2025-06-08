package ru.practice.t_finance.presentation.screens.authentication.consentCode

sealed class UserState {
    object Initial : UserState()
    object Loading : UserState()
    data class Success(val isRegistered: Boolean = false) : UserState()
    data class Error(val message: String) : UserState()

}