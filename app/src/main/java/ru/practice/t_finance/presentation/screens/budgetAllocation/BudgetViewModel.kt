package ru.practice.t_finance.presentation.screens.budgetAllocation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.usecases.BudgetUseCase
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val useCase: BudgetUseCase
): ViewModel(){


    var step by mutableStateOf(1)
        private set

    private val _budgetAmount = MutableStateFlow(0L)
    val budgetAmount : StateFlow<Long> = _budgetAmount.asStateFlow()

    private val _categoryStateFlow = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val categoryState: StateFlow<CategoryState> = _categoryStateFlow.asStateFlow()

    var selectedCategories = mutableListOf<Category>()

    fun nextStep(){
        if (step < 2 ) step ++
    }

    fun previousStep(){
        if (step > 1 ) step--
    }

    fun setBudgetAmount(amount: Long){
        _budgetAmount.value = amount
    }

    fun getCategories(){
         viewModelScope.launch{
            runCatching {
                useCase.getCategories()
            }.onSuccess { categories ->
                _categoryStateFlow.value = CategoryState.Success(categories)
            }.onFailure { throwable ->
                _categoryStateFlow.value = CategoryState.Error(throwable.message ?: "Unknown error")
            }
        }
    }

}

sealed class CategoryState{
    data class Success(val categories: List<Category>) : CategoryState()
    data class Error(val message: String) : CategoryState()
    object Loading : CategoryState()
}