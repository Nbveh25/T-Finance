package ru.practice.t_finance.presentation.screens.goal.goalEdit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import ru.practice.t_finance.domain.model.EditGoalModel
import ru.practice.t_finance.domain.model.GetGoalModel
import ru.practice.t_finance.domain.usecases.goal.EditGoalUseCase
import ru.practice.t_finance.domain.usecases.goal.GetGoalByIdUseCase
import ru.practice.t_finance.domain.util.DateFormatter
import ru.practice.t_finance.domain.validator.GoalEditValidator

@HiltViewModel
class GoalEditViewModel @Inject constructor(
    private val editGoalUseCase: EditGoalUseCase,
    private val getGoalByIdUseCase: GetGoalByIdUseCase,
    private val goalEditValidator: GoalEditValidator
): ViewModel() {
    private  val _state = MutableStateFlow<GoalEditScreenState>(GoalEditScreenState.Initial)
    internal val state: StateFlow<GoalEditScreenState> = _state.asStateFlow()

    internal var errorMessage by mutableStateOf<String?>(null)

    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var term by mutableStateOf("")
    var amount by mutableStateOf("")
    var accumulatedAmount by mutableStateOf("")

    @RequiresApi(Build.VERSION_CODES.O)
    fun editGoal(goalId: Int) {
        if (!goalEditValidator.amountValid(amount)) {
            errorMessage = "Неверный формат суммы"
            return
        }

        viewModelScope.launch {
            _state.value = GoalEditScreenState.Loading
            Log.d("GoalEditViewModel", term)
            Log.d("GoalEditViewModel", accumulatedAmount)
            //Log.d("GoalEditViewModel", DateFormatter.reverseFormat(term))
            editGoalUseCase.invoke(
                EditGoalModel(
                    id = goalId,
                    name = name,
                    term = DateFormatter.format(term),
                    amount = amount.toDouble(),
                    description = description,
                )
            ).onSuccess { data ->

                _state.value = GoalEditScreenState.Success(navigation = true)
            }.onFailure { error ->

                _state.value = GoalEditScreenState.Error(error.message ?: "Ошибка")
            }
        }
    }

    fun getGoal(goalId: Int) {

        viewModelScope.launch {
            _state.value = GoalEditScreenState.Loading

            getGoalByIdUseCase.invoke(id = goalId).onSuccess { data ->
                name = data.name
                description = data.description
                term = DateFormatter.reverseFormat(data.term)
                amount = data.amount.toString()

                _state.value = GoalEditScreenState.Success(navigation = false)
            }.onFailure { message ->

                _state.value = GoalEditScreenState.Error(message = "Ошибка: $message")
            }
        }
    }

    fun updateName(newName: String) {
        name = newName
    }

    fun updateDescription(newDesc: String) {
        description = newDesc
    }

    fun updateTerm(newTerm: String) {
        term = newTerm
    }

    fun updateAmount(newAmount: String) {
        amount = newAmount
    }

}