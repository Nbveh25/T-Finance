package ru.practice.t_finance.presentation.screens.authentication.auth

sealed class AuthScreenState {
    object Initial : AuthScreenState()
    object Loading : AuthScreenState()
    object Success : AuthScreenState()
    data class Error(val message: String) : AuthScreenState()
}