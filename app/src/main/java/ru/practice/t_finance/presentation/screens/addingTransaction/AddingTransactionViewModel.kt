package ru.practice.t_finance.presentation.screens.addingTransaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.model.GetCategoryModel
import ru.practice.t_finance.domain.usecases.GetCategoriesUseCase
import ru.practice.t_finance.domain.usecases.addingTransaction.AddingTransactionUseCase
import ru.practice.t_finance.presentation.screens.goal.goalEdit.GoalEditScreenState

@HiltViewModel
class AddingTransactionViewModel @Inject constructor(
    private val addingTransactionUseCase: AddingTransactionUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private  val _state = MutableStateFlow<AddingTransactionScreenState>(AddingTransactionScreenState.Initial)
    internal val state: StateFlow<AddingTransactionScreenState> = _state.asStateFlow()

    var categories: List<GetCategoryModel> = emptyList()

    init {
        getCategories()
    }

    fun getCategories() {

        viewModelScope.launch {
            getCategoriesUseCase.invoke().onSuccess { data ->
                categories = data
                Log.d("AddingTransactionViewModel", data.toString())
                _state.value = AddingTransactionScreenState.Success(data)
            }.onFailure { error ->

                _state.value = AddingTransactionScreenState.Error(error.message ?: "Ошибка")
            }
        }

    }

}