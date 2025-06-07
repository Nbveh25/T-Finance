package ru.practice.t_finance.presentation.screens.main

import ru.practice.t_finance.presentation.model.GoalItem

sealed class GoalUIState {
    object Initial: GoalUIState()
    object Loading: GoalUIState()
    data class Success(val data: List<GoalItem>): GoalUIState()
    data class Error(val message: String): GoalUIState()
}