package ru.practice.t_finance.presentation.screens.goal.goalCreate

sealed class GoalCreateScreenState {
    object Initial: GoalCreateScreenState()
    object Loading: GoalCreateScreenState()
    object Success: GoalCreateScreenState()
    data class Error(val message: String): GoalCreateScreenState()
}