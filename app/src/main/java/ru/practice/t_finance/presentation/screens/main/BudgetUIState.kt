package ru.practice.t_finance.presentation.screens.main

import ru.practice.t_finance.presentation.model.BudgetItem

sealed class BudgetUIState {
    object Initial: BudgetUIState()
    object Loading: BudgetUIState()
    data class Success(val data: BudgetItem): BudgetUIState()
    data class Error(val message: String): BudgetUIState()
}