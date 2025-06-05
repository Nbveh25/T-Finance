package ru.practice.t_finance.presentation.screens.goal.goalEdit

sealed class GoalEditScreenState {
    object Initial: GoalEditScreenState()
    object Loading: GoalEditScreenState()
    data class Success(val navigation: Boolean = false): GoalEditScreenState()
    data class Error(val message: String): GoalEditScreenState()
}