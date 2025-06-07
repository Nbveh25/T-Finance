package ru.practice.t_finance.presentation.screens.main

import ru.practice.t_finance.presentation.model.TransactionListItem

sealed class TransactionUIState {
    object Initial: TransactionUIState()
    object Loading: TransactionUIState()
    data class Success(val data: List<TransactionListItem>): TransactionUIState()
    data class Error(val message: String): TransactionUIState()
}