package ru.practice.t_finance.presentation.screens.authentication.consentCode

internal sealed class ConsentCodeState {
    object Initial : ConsentCodeState()
    object Loading : ConsentCodeState()
    object Success : ConsentCodeState()
    object CodeResent : ConsentCodeState()
    class Error(val message: String) : ConsentCodeState()
}