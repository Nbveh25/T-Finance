package ru.practice.t_finance.presentation.screens.addingTransaction

sealed class AddingTransactionScreenState {
    object Initial : AddingTransactionScreenState()
    object Loading : AddingTransactionScreenState()
    data class Success(val navigation: Boolean = false) : AddingTransactionScreenState()
    data class Error(val error: String) : AddingTransactionScreenState()

}