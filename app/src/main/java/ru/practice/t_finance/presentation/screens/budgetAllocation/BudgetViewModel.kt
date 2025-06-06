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
import ru.practice.t_finance.domain.usecases.budgetAllocation.BudgetUseCase
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.graphics.Color
import org.threeten.bp.LocalDateTime
import ru.practice.t_finance.domain.usecases.budgetAllocation.SendCategoriesToNetworkUseCase
import ru.practice.t_finance.domain.usecases.budgetAllocation.SendSumBudgetUseCase

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val useCase: BudgetUseCase,
    private val sendCategoriesToNetworkUseCase: SendCategoriesToNetworkUseCase,
    private val sendSumBudgetUseCase: SendSumBudgetUseCase
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
             _categoryStateFlow.emit(CategoryState.Loading)
             useCase.invoke().onSuccess { data ->
                 val categories = data.map { category ->
                     Category(
                         id = category.id,
                         name = category.name,
                         color = category.color.toColor()
                     )
                 }
                 _categoryStateFlow.emit(
                     CategoryState.SuccessCategories(
                         categories = categories
                     )
                 )
             }.onFailure { error ->
                 _categoryStateFlow.emit(
                     CategoryState.Error(error.message ?: "Error")
                 )
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

    fun sendData(){
        viewModelScope.launch {
            _categoryStateFlow.emit(CategoryState.Loading)
            val categories = _selectedCategories.value.values.toList()
            val budget = _elementaryBudget.value
            val now = LocalDateTime.now()
            val dayOfMonth = now.dayOfMonth
            sendSumBudgetUseCase.invoke(budget.toString(),dayOfMonth.toString()).onSuccess {
                sendCategoriesToNetworkUseCase.invoke(categories).onSuccess {
                    _categoryStateFlow.emit(
                        CategoryState.SuccessNetwork
                    )
                }.onFailure { data ->
                    _categoryStateFlow.emit(
                        CategoryState.Error(data.message ?: "Error")
                    )
                }
            }.onFailure { data ->
                _categoryStateFlow.emit(
                    CategoryState.Error(data.message ?: "Error")
                )
            }
        }
    }

}


fun String.toColor(): Color {
    var colorString = this
    // Удаляем # если есть
    if (colorString.startsWith("#")) {
        colorString = colorString.substring(1)
    }
    // Добавляем альфа-канал если его нет (FF - полностью непрозрачный)
    if (colorString.length == 6) {
        colorString = "FF$colorString"
    }
    // Конвертируем в long и создаем Color
    return Color(colorString.toLong(16))
}

sealed class CategoryState{
    data class SuccessCategories(val categories: List<Category>) : CategoryState()
    data class Error(val message: String) : CategoryState()
    object Loading : CategoryState()
    object SuccessNetwork : CategoryState()

}
