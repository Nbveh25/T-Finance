package ru.practice.t_finance.presentation.screens.goal.goalCreate

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
import ru.practice.t_finance.domain.model.CreateGoalModel
import ru.practice.t_finance.domain.usecases.goal.CreateGoalUseCase
import ru.practice.t_finance.domain.util.DateFormatter
import ru.practice.t_finance.domain.validator.GoalEditValidator

@HiltViewModel
class GoalCreateViewModel @Inject constructor(
    private val createGoalUseCase: CreateGoalUseCase,
    private val goalEditValidator: GoalEditValidator
): ViewModel() {
    private  val _state = MutableStateFlow<GoalCreateScreenState>(GoalCreateScreenState.Initial)
    internal val state: StateFlow<GoalCreateScreenState> = _state.asStateFlow()

    internal var errorMessage by mutableStateOf<String?>(null)

    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var term by mutableStateOf("")
    var amount by mutableStateOf("")


    @RequiresApi(Build.VERSION_CODES.O)
    fun createGoal() {
        if (!validateFields()) return

        viewModelScope.launch {
            _state.value = GoalCreateScreenState.Loading
            Log.d("GoalEditViewModel", DateFormatter.format(term))
            createGoalUseCase.invoke(
                CreateGoalModel(
                    name = name,
                    term = term,
                    amount = amount.toDouble(),
                    description = description,
                    accumulatedAmount = 0.0
                )
            ).onSuccess {

                _state.value = GoalCreateScreenState.Success
            }.onFailure { error ->

                _state.value = GoalCreateScreenState.Error(error.message ?: "Ошибка")
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

    private fun validateFields(): Boolean {
        return when {
            name.isBlank() -> { errorMessage = "Введите название цели"; false }
            term.isBlank() -> { errorMessage = "Введите срок цели"; false }
            amount.isBlank() -> { errorMessage = "Введите сумму цели"; false }
            !goalEditValidator.amountValid(amount) -> { errorMessage = "Неверный формат суммы"; false }
            else -> true
        }
    }
}

