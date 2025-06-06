package ru.practice.t_finance.presentation.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.practice.t_finance.presentation.model.TransactionListItem
import javax.inject.Inject

@HiltViewModel
class MainVewModel @Inject constructor(

) : ViewModel() {
}

sealed class MainUiState(){
    object Loading : MainUiState()
    data class Success(
        val Transactions : List<TransactionListItem>
    ) : MainUiState()
    data class Error(val message: String) : MainUiState()
}