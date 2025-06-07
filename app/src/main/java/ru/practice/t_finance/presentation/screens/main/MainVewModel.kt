package ru.practice.t_finance.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.usecases.goal.GetGoalsUseCase
import ru.practice.t_finance.domain.usecases.main.GetBudgetUseCase
import ru.practice.t_finance.domain.usecases.main.GetTransactionsUseCase
import ru.practice.t_finance.presentation.mapper.toItem
import ru.practice.t_finance.presentation.mapper.toListItem
import javax.inject.Inject

@HiltViewModel
class MainVewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val getBudgetUseCase: GetBudgetUseCase
) : ViewModel() {

    private val _budgetState = MutableStateFlow<BudgetUIState>(BudgetUIState.Initial)
    internal val budgetState: StateFlow<BudgetUIState> = _budgetState.asStateFlow()

    private val _transactionsState = MutableStateFlow<TransactionUIState>(TransactionUIState.Initial)
    internal val transactionsState: StateFlow<TransactionUIState> = _transactionsState.asStateFlow()

    private val _goalsState = MutableStateFlow<GoalUIState>(GoalUIState.Initial)
    internal val goalsState: StateFlow<GoalUIState> = _goalsState.asStateFlow()

    init {
        getBudget()
        getTransactions()
        getGoals()
    }

    fun getBudget() {
        viewModelScope.launch {
            _budgetState.value = BudgetUIState.Initial

            getBudgetUseCase.invoke().onSuccess { data ->

                _budgetState.value = BudgetUIState.Success(data.toItem())
            }.onFailure { error ->

                _budgetState.value = BudgetUIState.Error("${error.message}")
            }

        }
    }

    fun getTransactions() {
        viewModelScope.launch {
            _transactionsState.value = TransactionUIState.Loading

            getTransactionsUseCase.invoke().onSuccess { data ->

                _transactionsState.value = TransactionUIState.Success(data.map { it.toListItem() })
            }.onFailure { error ->

                _transactionsState.value = TransactionUIState.Error("Error:  ${error.message}")
            }

        }
    }

    fun getGoals() {
        viewModelScope.launch {
            _goalsState.value = GoalUIState.Loading

            getGoalsUseCase.invoke().onSuccess { data ->

                _goalsState.value = GoalUIState.Success(data = data.map { it.toItem() })
            }.onFailure { error ->

                _goalsState.value = GoalUIState.Error(message = "Error: ${error.message}")
            }
        }
    }

}

