package ru.practice.t_finance.presentation.screens.goal.goalList

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import ru.practice.t_finance.domain.usecases.goal.GetGoalsUseCase
import ru.practice.t_finance.domain.util.DateFormatter

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val getGoalsUseCase: GetGoalsUseCase,
) : ViewModel() {

    private  val _state = MutableStateFlow<GoalListScreenState>(GoalListScreenState.Initial)
    internal val state: StateFlow<GoalListScreenState> = _state.asStateFlow()

    var goalList = mutableListOf<GoalModel>()

    init {
        Log.d("GoalViewModel", "init")
        getGoals()
    }

    fun getGoals() {
        viewModelScope.launch {
            _state.value = GoalListScreenState.Loading
            getGoalsUseCase.invoke().onSuccess { data ->
                goalList = data.toMutableList()
                _state.value = GoalListScreenState.Success()
            }.onFailure { error ->
                _state.value = GoalListScreenState.Error(error.message ?: "Ошибка")
            }
        }
    }

}