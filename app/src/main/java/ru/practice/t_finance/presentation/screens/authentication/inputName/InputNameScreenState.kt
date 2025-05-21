package ru.practice.t_finance.presentation.screens.authentication.inputName

internal sealed class InputNameScreenState {
    object Initial : InputNameScreenState()
    object Loading : InputNameScreenState()
    object Success : InputNameScreenState()
    data class Error(val message: String) : InputNameScreenState()
}