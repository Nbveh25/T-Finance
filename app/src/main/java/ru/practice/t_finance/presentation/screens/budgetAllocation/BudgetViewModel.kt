package ru.practice.t_finance.presentation.screens.budgetAllocation


import android.util.Log
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.model.Category
import ru.practice.t_finance.domain.usecases.BudgetUseCase
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val useCase: BudgetUseCase
): ViewModel(){



    private val _categoryStateFlow = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val categoryState: StateFlow<CategoryState> = _categoryStateFlow.asStateFlow()

    private val _selectedCategories = mutableStateOf<Map<String, Category>>(mutableMapOf())
    val selectedCategories: State<Map<String, Category>> = _selectedCategories


    private val _selectedCategoryForEdit = MutableStateFlow<Category?>(null)
    val selectedCategoryForEdit = _selectedCategoryForEdit

    private val _elementaryBudget = MutableStateFlow<Int>(0)
    val elementaryBudget = _elementaryBudget

    private val _remainingBudget = MutableStateFlow<Int>(0)
    val remainingBudget = _remainingBudget

    val isBudgetFullyAllocated: State<Boolean> = derivedStateOf {
//        val totalAllocated = selectedCategories.value.values.sumOf { category ->
//            (category.value * elementaryBudget.value) / 100
//        }
//        totalAllocated == elementaryBudget.value
        val selected = selectedCategories.value.values.toList()
        val allocatedMap = allocateBudget(selected, elementaryBudget.value)
        val totalAllocated = allocatedMap.values.sum()
        totalAllocated == elementaryBudget.value
    }


    fun setElementaryBudget(budgetValue: String){
        _elementaryBudget.value = budgetValue.toInt()
        remainingBudget.value = budgetValue.toInt()
    }

    fun reduceRemainingBudget(category: Category) {
        val amount = (category.value * elementaryBudget.value) / 100
        _remainingBudget.value -= amount
        Log.d("MyLog",_remainingBudget.value.toString())
    }

    fun increaseRemainingBudget(category: Category) {
        val amount = (category.value * elementaryBudget.value) / 100
        _remainingBudget.value += amount
    }

    fun addToSelectedCategories(category: Category, percent: Int) {
        val updated = category.copy(value = percent)
        val currentMap = _selectedCategories.value.toMutableMap()
        currentMap.put(updated.name,updated)
        _selectedCategories.value = currentMap
        reduceRemainingBudget(updated)
    }


    fun deleteCategory(category: Category){
        val currentMap = _selectedCategories.value.toMutableMap()
        currentMap.remove(category.name)
        _selectedCategories.value = currentMap
        increaseRemainingBudget(category)
    }

    fun openBottomSheetFor(category: Category) {
        _selectedCategoryForEdit.value = category
    }

    fun closeBottomSheet() {
        _selectedCategoryForEdit.value = null
    }


    fun getCategories(){
         viewModelScope.launch{
            runCatching {
                useCase.getCategories()
            }.onSuccess { categories ->
                //_categoryStateFlow.value = CategoryState.Success(categories)
            }.onFailure { throwable ->
                //_categoryStateFlow.value = CategoryState.Error(throwable.message ?: "Unknown error")
            }
        }
    }

    fun allocateBudget(
        categories: List<Category>,
        totalBudget: Int
    ): Map<String, Int> {
        val result = mutableMapOf<String, Int>()
        val itemsWithFraction = categories.map { category ->
            val allocated = (totalBudget.toDouble() * category.value) / 100.0
            val floorAllocated = allocated.toInt()
            val fraction = allocated - floorAllocated
            Triple(category.name, floorAllocated, fraction)
        }

        var sum = itemsWithFraction.sumOf { it.second }
        var remaining = totalBudget - sum

        // Сортируем по убыванию дробной части
        val sortedItems = itemsWithFraction.sortedByDescending { it.third }

        // Распределяем оставшиеся деньги
        for ((name, allocated, _) in sortedItems) {
            if (remaining > 0) {
                result[name] = allocated + 1
                remaining--
            } else {
                result[name] = allocated
            }
        }

        return result
    }

}

sealed class CategoryState{
    data class Success(val categories: List<Category>) : CategoryState()
    data class Error(val message: String) : CategoryState()
    object Loading : CategoryState()
}