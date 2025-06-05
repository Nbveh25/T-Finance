package ru.practice.t_finance.presentation.screens.goal.goalDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.usecases.goal.GetGoalByIdUseCase
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val getGoalByIdUseCase: GetGoalByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<GoalDetailScreenState>(GoalDetailScreenState.Initial)
    internal val state: StateFlow<GoalDetailScreenState> = _state.asStateFlow()

    fun getGoal(goalId: Int) {

        viewModelScope.launch {
            _state.value = GoalDetailScreenState.Loading

            getGoalByIdUseCase.invoke(id = goalId).onSuccess { data ->

                _state.value = GoalDetailScreenState.Success(data)
            }.onFailure { message ->

                _state.value = GoalDetailScreenState.Error(message = "Ошибка: $message")
            }
        }
    }

}