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
import ru.practice.t_finance.domain.model.GoalModel
import ru.practice.t_finance.domain.usecases.goal.CreateGoalUseCase
import ru.practice.t_finance.domain.util.DateFormatter
import ru.practice.t_finance.domain.validator.GoalEditValidator

@HiltViewModel
class GoalEditViewModel @Inject constructor(
    private val createGoalUseCase: CreateGoalUseCase,
    private val goalEditValidator: GoalEditValidator
): ViewModel() {
    private  val _state = MutableStateFlow<GoalEditScreenState>(GoalEditScreenState.Initial)
    internal val state: StateFlow<GoalEditScreenState> = _state.asStateFlow()

    internal var errorMessage by mutableStateOf<String?>(null)

    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var term by mutableStateOf("")
    var amount by mutableStateOf("")


    @RequiresApi(Build.VERSION_CODES.O)
    fun createGoal() {
        if (!goalEditValidator.amountValid(amount)) {
            errorMessage = "Неверный формат суммы"
            return
        }

        viewModelScope.launch {
            _state.value = GoalEditScreenState.Loading
            Log.d("GoalEditViewModel", DateFormatter.format(term))
            createGoalUseCase.invoke(
                GoalModel(
                    name = name,
                    term = term,
                    amount = amount.toDouble(),
                    description = description,
                    accumulatedAmount = 0.0
                )
            ).onSuccess {

                _state.value = GoalEditScreenState.Success
            }.onFailure { error ->

                _state.value = GoalEditScreenState.Error(error.message ?: "Ошибка")
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