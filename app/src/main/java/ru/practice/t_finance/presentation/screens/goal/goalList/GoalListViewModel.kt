package ru.practice.t_finance.presentation.screens.goal.goalList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practice.t_finance.domain.usecases.goal.GetGoalsUseCase
import ru.practice.t_finance.presentation.mapper.toItem
import ru.practice.t_finance.presentation.model.GoalItem

@HiltViewModel
class GoalListViewModel @Inject constructor(
    private val getGoalsUseCase: GetGoalsUseCase,
) : ViewModel() {

    private  val _state = MutableStateFlow<GoalListScreenState>(GoalListScreenState.Initial)
    internal val state: StateFlow<GoalListScreenState> = _state.asStateFlow()

    var goalList: List<GoalItem> = emptyList()

    init {
        Log.d("GoalViewModel", "init")
        getGoals()
    }

    fun getGoals() {
        viewModelScope.launch {
            _state.value = GoalListScreenState.Loading
            getGoalsUseCase.invoke().onSuccess { data ->
                goalList = data.map { goal ->
                    goal.toItem()
                }
                _state.value = GoalListScreenState.Success()
            }.onFailure { error ->
                _state.value = GoalListScreenState.Error(error.message ?: "Ошибка")
            }
        }
    }

}