package ru.practice.t_finance.presentation.screens.goal.goalEdit

sealed class GoalEditScreenState {
    object Initial: GoalEditScreenState()
    object Loading: GoalEditScreenState()
    object Success: GoalEditScreenState()
    data class Error(val message: String): GoalEditScreenState()
}