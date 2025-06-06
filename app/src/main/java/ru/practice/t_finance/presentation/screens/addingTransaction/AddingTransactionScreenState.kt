package ru.practice.t_finance.presentation.screens.addingTransaction

import ru.practice.t_finance.domain.model.GetCategoryModel

sealed class AddingTransactionScreenState {
    object Initial : AddingTransactionScreenState()
    object Loading : AddingTransactionScreenState()
    data class Success(val data: List<GetCategoryModel>) : AddingTransactionScreenState()
    data class Error(val error: String) : AddingTransactionScreenState()

}