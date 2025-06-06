package ru.practice.t_finance.presentation.screens.addingTransaction

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.model.AddingTransactionModel
import ru.practice.t_finance.domain.model.GetCategoryModel
import ru.practice.t_finance.domain.usecases.GetCategoriesUseCase
import ru.practice.t_finance.domain.usecases.addingTransaction.AddingTransactionUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltViewModel
class AddingTransactionViewModel @Inject constructor(
    private val addingTransactionUseCase: AddingTransactionUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AddingTransactionScreenState>(AddingTransactionScreenState.Initial)
    internal val state: StateFlow<AddingTransactionScreenState> = _state.asStateFlow()

    var categories: List<GetCategoryModel> = emptyList()

    // Состояния формы
    var amount by mutableStateOf("")
    var selectedDate by mutableStateOf(Date())
    var description by mutableStateOf("")

    var errorMessage by mutableStateOf<String?>(null)

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.invoke()
                .onSuccess { data ->
                    categories = data
                    _state.value = AddingTransactionScreenState.Success(navigation = false)
                }
                .onFailure { error ->
                    _state.value = AddingTransactionScreenState.Error(error.message ?: "Ошибка загрузки категорий")
                }
        }
    }

    fun addTransaction(selectedCategory: GetCategoryModel) {
        viewModelScope.launch {
            if (amount.isBlank()) {
                errorMessage = "Заполните все обязательные поля"
                _state.value = AddingTransactionScreenState.Error("Заполните все обязательные поля")
                return@launch
            }

            try {
                val transaction = AddingTransactionModel(
                    name = "Добавленная транзакция",
                    date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(selectedDate),
                    categoryId = selectedCategory.id,
                    amount = amount.toDouble(),
                    description = description
                )

                Log.d("Transaction", transaction.toString())

                addingTransactionUseCase.invoke(transaction)
                    .onSuccess {
                        Log.d("AddingTransactionViewModel", "Success")
                        _state.value = AddingTransactionScreenState.Success(navigation = true)
                        resetForm()
                    }
                    .onFailure { error ->
                        Log.d("AddingTransactionViewModel", "${error.message}")
                        _state.value = AddingTransactionScreenState.Error(error.message ?: "Ошибка добавления")
                    }
            } catch (e: NumberFormatException) {
                _state.value = AddingTransactionScreenState.Error("Некорректный формат суммы")
            }
        }
    }

    fun updateAmount(newAmount: String) {
        amount = newAmount.filter { it.isDigit() || it == '.' }
        errorMessage = null
    }


    fun updateDate(newDate: Date) {
        selectedDate = newDate
        errorMessage = null
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
        errorMessage = null
    }

    private fun resetForm() {
        amount = ""
        selectedDate = Date()
        description = ""
        errorMessage = null
    }
}